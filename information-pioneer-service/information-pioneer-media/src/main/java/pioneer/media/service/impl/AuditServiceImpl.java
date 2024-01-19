package pioneer.media.service.impl;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.redisson.api.RBlockingDeque;
import org.redisson.api.RDelayedQueue;
import org.redisson.api.RedissonClient;
import org.springframework.scheduling.annotation.Async;
import pioneer.common.aliyun.GreenImageScan;
import pioneer.common.aliyun.GreenTextScan;
import pioneer.common.dto.ResponseResult;
import pioneer.common.minio.MinIOService;
import pioneer.common.util.SensitiveWordUtil;
import pioneer.media.client.ArticleFeign;
import pioneer.media.dto.ArticleDto;
import pioneer.media.dto.ContentDto;
import pioneer.media.dto.ImageDto;
import pioneer.media.entity.*;
import pioneer.media.service.*;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class AuditServiceImpl implements IAuditService {


    @Autowired
    private GreenTextScan greenTextScan;

    @Autowired
    private IWmNewsService wmNewsService;

    @Autowired
    private MinIOService minIOService;

    @Autowired
    private GreenImageScan greenImageScan;

    @Autowired
    private ArticleFeign articleFeign;

    @Autowired
    private IWmUserService wmUserService;

    @Autowired
    private IWmSensitiveService wmSensitiveService;

    @Autowired
    private IWmChannelService wmChannelService;

    @Autowired
    private RedissonClient redissonClient;


    @Override
    public void audit(WmNews wmNews) {
        //自己管理敏感词审核  DFA算法
        boolean flag =  checkSensitive(wmNews);
        if(!flag)return;

//        文本审核
        flag =  checkText(wmNews);
        if(!flag)return;
//     图片审核
        flag =  checkImages(wmNews);
        if(!flag)return;

//        还需要判断是否到达发布时间
        if(new Date().getTime() > wmNews.getPublishTime().getTime() ){
//            达到发布时间
            Long articleId = createApArticle(wmNews);
            wmNews.setArticleId(articleId);
            wmNews.setStatus(9);
            wmNews.setSubmitedTime(new Date());
            wmNewsService.updateById(wmNews);
        }else{
            wmNews.setStatus(8);
            wmNews.setSubmitedTime(new Date());
            wmNewsService.updateById(wmNews);
        }

    }
    @Override
    public void audit(Integer id) {
        WmNews wmNews = wmNewsService.getById(id);
        //审核敏感词
        boolean b = checkSensitive(wmNews);
        if (b){
            //判断是否到了发布时间
            if (wmNews.getPublishTime().getTime()<=System.currentTimeMillis()) {
                //远程调用新增app文章
                Long articleId = this.createApArticle(wmNews);
                //审核通过，已经发布到App了
                wmNews.setArticleId(articleId);
                wmNews.setStatus(9);
                wmNews.setSubmitedTime(new Date());
                wmNewsService.updateById(wmNews);

            }else {
                //未到达发布时间
                wmNews.setStatus(8);
                wmNews.setSubmitedTime(new Date());
                wmNewsService.updateById(wmNews);

                //向Redis去发送一个延迟的任务
                RBlockingDeque<Object> blockingDeque = redissonClient.getBlockingDeque("publish-task");
                RDelayedQueue<Object> delayedQueue = redissonClient.getDelayedQueue(blockingDeque);
                //指定多少秒后才发送
                long time = wmNews.getPublishTime().getTime()-System.currentTimeMillis();
                delayedQueue.offer(wmNews.getId(),time, TimeUnit.MILLISECONDS);
            }
        }
    }

    //使用Spring中的线程池(指定Bean的名字)来异步处理逻辑,用户请求完直接返回，异步处理审核，防止审核时间过长，写一个TaskPoolConfig对线程池参数进行修改
    //加上之后如果当前类存在循环依赖的问题（a里面注入b b里面注入a）则会报错,需要在对应的注入地方加上@lazy注解
    @Async("asyncExecutor")
    public boolean checkSensitive(WmNews wmNews) {
        //查询所有敏感词
        List<WmSensitive> wmSensitives = wmSensitiveService.list();

        List<String> collect = wmSensitives.stream().map(s -> {
            return s.getSensitives();
        }).collect(Collectors.toList());
        //调用DFA工具类初始一个敏感词map
        SensitiveWordUtil.initMap(collect);

        String title = wmNews.getTitle();
        List<ContentDto> contentDtos = JSON.parseArray(wmNews.getContent(), ContentDto.class);
        String text = contentDtos.stream().map(s -> {
            if (s.getType().equals("text")) {
                return s.getValue();
            }
            return "";
        }).collect(Collectors.joining(","));

        //匹配是否包含敏感词
        Map<String, Integer> stringIntegerMap = SensitiveWordUtil.matchWords(text + title);
        if (stringIntegerMap.size()==0){
            //说明没有包含敏感词
            return true;
        }
        Set<String> strings = stringIntegerMap.keySet();
        String join = StringUtils.join(strings, ",");

        wmNews.setStatus(2); //审核失败
        wmNews.setReason("图片审核审核失败，包含如下敏感词："+join);
        wmNewsService.updateById(wmNews);
        return false;
    }
    public Long createApArticle(WmNews wmNews) {
        ArticleDto articleDto = new ArticleDto();
        articleDto.setContent(wmNews.getContent());
        articleDto.setTitle(wmNews.getTitle());

//        wmNews.getUserId() 这是自媒体人的id
        WmUser wmuser = wmUserService.getById(wmNews.getUserId());

        articleDto.setAuthorId(wmuser.getApAuthorId());
        articleDto.setAuthorName(wmuser.getName());

        WmChannel wmChannel = wmChannelService.getById(wmNews.getChannelId());
        articleDto.setChannelId(wmChannel.getId());
        articleDto.setChannelName(wmChannel.getName());

        articleDto.setLayout(wmNews.getType());
        articleDto.setFlag(0);
        //自媒体文章图片格式：[{"id":96,"url":"https://myth.oss-cn-beijing.aliyuncs.com/62e84d50-a531-479e-b343-155ca1a8b611.jpeg"},{"id":97,"url":"https://myth.oss-cn-beijing.aliyuncs.com/d7364a4e-459e-4769-a10e-762de8b68d96.jpeg"},{"id":99,"url":"https://myth.oss-cn-beijing.aliyuncs.com/c02b70fa-d107-4200-af10-fdf87db18737.jpeg"}]
        //App文章的图片格式：https://myth.oss-cn-beijing.aliyuncs.com/78604213-d207-4e78-acc6-8d1b38562b81.jpeg
        List<ImageDto> imageDtos = JSON.parseArray(wmNews.getImages(), ImageDto.class);
        String imagesStr = imageDtos.stream().map(imageDto -> {
            return imageDto.getUrl();
        }).collect(Collectors.joining(","));

        articleDto.setImages(imagesStr);

        articleDto.setLabels(wmNews.getLabels());
        articleDto.setPublishTime(wmNews.getPublishTime());
        articleDto.setId(wmNews.getArticleId());


        ResponseResult<Long> responseResult = articleFeign.saveArticle(articleDto);
        if (responseResult.getCode()!=0){
            throw new RuntimeException("远程调用失败");
        }
        return responseResult.getData();

    }

    private boolean checkImages(WmNews wmNews) {
//        图片有两种：内容图片+封面图片
        List<String> allUrlList = new ArrayList<>();

//        获取内容图片
        List<ContentDto> contentDtoList = JSON.parseArray(wmNews.getContent(), ContentDto.class);
        for (ContentDto contentDto : contentDtoList) {
            if (contentDto.getType().equals("image")) {
                allUrlList.add(contentDto.getValue());
            }
        }

        String images = wmNews.getImages();
//        [{"id":262,"url":"http://192.168.85.143:9000/heima/91198f22-0274-4156-8d1b-09835e7990bd.jpeg"},
//        {"id":263,"url":"http://192.168.85.143:9000/heima/3b0383e6-18c7-4891-9606-6703c2b8d3e5.jpeg"},
//        {"id":264,"url":"http://192.168.85.143:9000/heima/4e342011-dd0c-4a5b-8005-ad065ab43056.jpeg"}]
        List<ImageDto> imageDtoList = JSON.parseArray(images, ImageDto.class);
        for (ImageDto imageDto : imageDtoList) {
            //去重
            if(!allUrlList.contains(imageDto.getUrl())){
                allUrlList.add(imageDto.getUrl());
            }
        }
//        要从minio中下载图片
        try {
            List<byte[]> byteList = new ArrayList<>();
            for (String url : allUrlList) {
                InputStream inputStream = minIOService.download(url);
                byte[] bytes = IOUtils.toByteArray(inputStream);
                byteList.add(bytes);
            }
            Map map = greenImageScan.imageScan(byteList);

            String suggestion = (String) map.get("suggestion");

            switch (suggestion){
                case "pass":{
                    return true;
                }
                case "review":{
                    String reason = (String) map.get("label"); //不通过时map有两对数据
                    wmNews.setStatus(3); //需要人工审核
                    wmNews.setReason("图片需要人工审核，图片审核不通过的原因是："+reason);
                    wmNewsService.updateById(wmNews);
                    return false;
                }
                case "block":{
                    String reason = (String) map.get("label"); //不通过时map有两对数据
                    wmNews.setStatus(2); //审核失败
                    wmNews.setReason("图片审核审核失败的原因是是："+reason);
                    wmNewsService.updateById(wmNews);
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;

    }

    private boolean checkText(WmNews wmNews) {
//        文本审核
//        文本=标题+文章内容
        String text = wmNews.getTitle();

//        获取文章内容
        //        文章内容格式如下
//        "[
//        {
//            "type":"text",
//                "value":"随着智能手机的普及，人们更加习惯于通过手机来看新闻。由于生活节奏的加快，很多人只能利用碎片时间来获取信息，因此，对于移动资讯客户端的需求也越来越高。黑马头条项目正是在这样背景下开发出来。黑马头条项目采用当下火热的微服务+大数据技术架构实现。本项目主要着手于获取最新最热新闻资讯，通过大数据分析用户喜好精确推送咨询新闻"
//        },
//        {
//            "type":"image",
//                "value":"http://192.168.200.130/group1/M00/00/00/wKjIgl5swbGATaSAAAEPfZfx6Iw790.png",
//                "id" :176
//        }
//]"
        List<ContentDto> contentDtoList = JSON.parseArray(wmNews.getContent(), ContentDto.class);
        for (ContentDto contentDto : contentDtoList) {
            if (contentDto.getType().equals("text")) {
                text += contentDto.getValue();
            }
        }
        try {
            Map map = greenTextScan.greenTextScan(text);
            String suggestion = (String) map.get("suggestion");
            switch (suggestion){
                case "pass":{
                    return true;
                }
                case "review":{
                    String reason = (String) map.get("label"); //不通过时map有两对数据
                    wmNews.setStatus(3); //需要人工审核
                    wmNews.setReason("文本需要人工审核，文本审核不通过的原因是："+reason);
                    wmNewsService.updateById(wmNews);
                    return false;
                }
                case "block":{
                    String reason = (String) map.get("label"); //不通过时map有两对数据
                    wmNews.setStatus(2); //需要人工审核
                    wmNews.setReason("文本审核审核失败的原因是是："+reason);
                    wmNewsService.updateById(wmNews);
                    return false;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
