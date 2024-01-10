package pioneer.media.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Async;
import pioneer.common.dto.PageResponseResult;
import pioneer.common.dto.ResponseResult;
import pioneer.common.dto.User;
import pioneer.common.enums.AppHttpCodeEnum;
import pioneer.common.util.SensitiveWordUtil;
import pioneer.common.util.UserThreadLocalUtil;
import pioneer.media.client.ArticleFeign;
import pioneer.media.dto.*;
import pioneer.media.entity.*;
import pioneer.media.mapper.WmNewsMapper;
import pioneer.media.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 自媒体图文内容信息表 服务实现类
 */
@Service
@Slf4j
public class WmNewsServiceImpl extends ServiceImpl<WmNewsMapper, WmNews> implements IWmNewsService {

    @Autowired
    private IWmNewsMaterialService iWmNewsMaterialService;

    @Autowired
    private IWmUserService wmUserService;

    @Autowired
    private IWmChannelService wmChannelService;

    @Autowired
    @Lazy
    private IAuditService auditService;

    @Autowired
    private IWmSensitiveService wmSensitiveService;

    @Autowired
    private ArticleFeign articleFeign;

    @Override
    public ResponseResult listByCondition(WmNewsPageDto dto) {

        if (dto == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        User user = UserThreadLocalUtil.get();
        if (user == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }

        LambdaQueryWrapper<WmNews> queryWrapper = new LambdaQueryWrapper<>();

        queryWrapper.eq(dto.getStatus()!=null,WmNews::getStatus,dto.getStatus());
        queryWrapper.like(StringUtils.isNotBlank(dto.getKeyword()),WmNews::getTitle,dto.getKeyword());
        queryWrapper.eq(dto.getChannelId()!=null,WmNews::getChannelId,dto.getChannelId());
        queryWrapper.between(dto.getBeginPubDate()!=null && dto.getEndPubDate() !=null,WmNews::getPublishTime,dto.getBeginPubDate(),dto.getEndPubDate());
        queryWrapper.eq(WmNews::getUserId,user.getUserId());
        queryWrapper.orderByDesc(WmNews::getPublishTime);

        Page<WmNews> wmNewsPage = new Page<>(dto.getPage(), dto.getSize());
        Page<WmNews> page = this.page(wmNewsPage, queryWrapper);

        return new PageResponseResult(dto.getPage(),dto.getSize(),page.getTotal(),page.getRecords());
    }

    @Override
    @GlobalTransactional
    public ResponseResult submit(WmNewsDto dto) {
        if (dto == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        User user = UserThreadLocalUtil.get();
        if (user == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }

        //判断是否有文章id，如果有就代表是修改，修改直接删掉相关联素材，重新发布素材
        Integer newsId = dto.getId();
        if (newsId!=null){
            deleteRelation(newsId);
        }
        WmNews wmNews = new WmNews();
        BeanUtils.copyProperties(dto,wmNews);

        wmNews.setUserId(user.getUserId());

        List<ImageDto> coverImageDtoList = dto.getImages(); //封面图片
        List<ImageDto> contentImageDtoList = getContentImageFromContent(dto.getContent()); //文章内容图片

        //0 无图文章  1 单图文章  3 多图文章
        if (dto.getType()==-1){ //自动从文章中获取封面
            int size = contentImageDtoList.size();
            if (size == 0){
                //无图文章
                wmNews.setType(0);
            } else if (size == 1 || size == 2) {
                //单图文章
                wmNews.setType(1);
                //封面图片应从内容图片中取一个
                coverImageDtoList = contentImageDtoList.subList(0,1);
            }else {
                //多图文章
                wmNews.setType(3);
                //封面图片应从内容图片中取一个
                coverImageDtoList = contentImageDtoList.subList(0,3);
            }
        }
        //设置封面图片
        wmNews.setImages(JSON.toJSONString(coverImageDtoList));
        wmNews.setEnable(true);
        wmNews.setCreatedTime(new Date());

        //id存在就update，不存在就save
        this.saveOrUpdate(wmNews);

        //判断是否为草稿
        if (dto.getStatus()==1) { //不是草稿，准备提交审核
            //向中间表插入数据
            saveRelation(coverImageDtoList,1,wmNews.getId());
            saveRelation(contentImageDtoList,0,wmNews.getId());

            //审核 涉及到阿里云审核，但是需要企业资质才可以使用，所以不实现，实现一个敏感词审核即可
//            auditService.audit(wmNews);
//            wmNews.setStatus(3); //需要人工审核
//            wmNews.setReason("图片需要人工审核");
//            this.updateById(wmNews);
//            return  ResponseResult.okResult();

            auditService.audit(wmNews.getId());

        }

        return ResponseResult.okResult();
    }

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
        this.updateById(wmNews);
        return false;
    }

    private Long createApArticle(WmNews wmNews) {
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
        articleDto.setPublishTime(wmNews.getPublishTime()); //TODO

        ResponseResult<Long> responseResult = articleFeign.saveArticle(articleDto);
        if (responseResult.getCode()!=0){
            throw new RuntimeException("远程调用失败");
        }
        return responseResult.getData();

    }

    private void saveRelation(List<ImageDto> imageDtoList,int type,Integer newsId){ //0:内容图片 1：封面图片
        int i = 0;
        for (ImageDto imageDto : imageDtoList) {
            WmNewsMaterial wmNewsMaterial = new WmNewsMaterial();
            wmNewsMaterial.setMaterialId(imageDto.getId());
            wmNewsMaterial.setNewsId(newsId);
            wmNewsMaterial.setType(type);
            wmNewsMaterial.setOrd(i);
            i++;

            iWmNewsMaterialService.save(wmNewsMaterial);
        }
    }
    private List<ImageDto> getContentImageFromContent(String content) {
//        "content":"[
//        {
//            "type":"text",
//                "value":"随着智能手机的普及，人们更加习惯于通过手机来看新闻。由于生活节奏的加快，很多人只能利用碎片时间来获取信息，因此，对于移动资讯客户端的需求也越来越高。本项目主要着手于获取最新最热新闻资讯，通过大数据分析用户喜好精确推送咨询新闻"
//        },
//        {
//            "type":"image",
//                "value":"http://192.168.200.130/group1/M00/00/00/wKjIgl5swbGATaSAAAEPfZfx6Iw790.png",
//                "id" :176
//        }
//        ]"
        ArrayList<ImageDto> imageDtoList = new ArrayList<>();

        //json对象是一个数组就需要用parseArray方法解析出集合
        List<ContentDto> contentDtos = JSON.parseArray(content, ContentDto.class);

        for (ContentDto contentDto : contentDtos) {
            if (contentDto.getType().equals("image")){
                ImageDto imageDto = new ImageDto();
                imageDto.setId(contentDto.getId());
                imageDto.setUrl(contentDto.getValue());
                imageDtoList.add(imageDto);
            }
        }
        return imageDtoList;
    }

    private void deleteRelation(Integer newsId) {
        LambdaQueryWrapper<WmNewsMaterial> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WmNewsMaterial::getNewsId,newsId);
        iWmNewsMaterialService.remove(queryWrapper);
    }
}
