package pioneer.article.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import pioneer.article.dto.ArticleDto;
import pioneer.article.dto.ArticleHomeDto;
import pioneer.article.dto.ArticleInfoDto;
import pioneer.article.entity.ApArticle;
import pioneer.article.entity.ApArticleContent;
import pioneer.article.mapper.ApArticleMapper;
import pioneer.article.service.IApArticleContentService;
import pioneer.article.service.IApArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import pioneer.article.task.CreateHtmlTask;
import pioneer.common.dto.ResponseResult;
import pioneer.common.enums.AppHttpCodeEnum;

import java.util.Date;
import java.util.HashMap;

/**
 * 文章信息表，存储已发布的文章 服务实现类
 */
@Service
public class ApArticleServiceImpl extends ServiceImpl<ApArticleMapper, ApArticle> implements IApArticleService {

    @Autowired
    private IApArticleContentService iApArticleContentService;

    @Autowired
    private CreateHtmlTask createHtmlTask;
    @Override
    public ResponseResult<Long> saveArticle(ArticleDto dto) {

        ApArticle apArticle = new ApArticle();
        BeanUtils.copyProperties(dto,apArticle);

        //判断是否有id，有id就是修改，没有id就是新增
        if (dto.getId()==null){
            apArticle.setCreatedTime(new Date());
            this.save(apArticle);

            ApArticleContent apArticleContent = new ApArticleContent();
            apArticleContent.setArticleId(apArticle.getId());
            apArticleContent.setContent(dto.getContent());
            iApArticleContentService.save(apArticleContent);

            //新增文章时生成静态页面放入minio中
            createHtmlTask.createHtml(apArticle,dto.getContent());

            return ResponseResult.okResult(apArticle.getId());
        }else {
            ApArticle byId = this.getById(dto.getId());
            if (byId==null){
                return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
            }

            this.updateById(apArticle);

            LambdaUpdateWrapper<ApArticleContent> updateWrapper = new LambdaUpdateWrapper<>();
            updateWrapper.set(ApArticleContent::getContent,dto.getContent())
                    .eq(ApArticleContent::getArticleId,dto.getId());

            iApArticleContentService.update(updateWrapper);

            //修改文章时生成静态页面放入minio中
            createHtmlTask.createHtml(apArticle,dto.getContent());
            return ResponseResult.okResult(apArticle.getId());
        }
    }

    @Override
    public ResponseResult load(ArticleHomeDto dto,int type) { //1:首次加载 2:加载更多 3:加载最新
        LambdaQueryWrapper<ApArticle> queryWrapper = new LambdaQueryWrapper<>();

        //过滤已删除和已下架的
        queryWrapper.eq(ApArticle::getIsDown,false);
        queryWrapper.eq(ApArticle::getIsDelete,false);

        if (dto.getChannelId()!=0){ //0代表是推荐频道，代表要查询全部频道
            queryWrapper.eq(ApArticle::getChannelId,dto.getChannelId());
        }

            //加载更多 发布时间<minTime
        if (type == 2){
            queryWrapper.lt(ApArticle::getPublishTime,dto.getMinTime());
        }else{
            //加载最新和首次加载 发布时间>maxTime
            queryWrapper.gt(ApArticle::getPublishTime,dto.getMaxTime());
        }
        //排序
        queryWrapper.orderByDesc(ApArticle::getPublishTime);

        //传1是因为经过排序和发布时间过滤之后，每次查的都是最新的数据，不需要分页
        Page<ApArticle> apArticlePage = new Page<>(1,dto.getSize());
        Page<ApArticle> page = this.page(apArticlePage, queryWrapper);

        return ResponseResult.okResult(page.getRecords());
    }

    @Override
    public ResponseResult loadArticleInfo(ArticleInfoDto dto) {
        if (dto == null || dto.getArticleId() == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);
        }
        HashMap resultMap = new HashMap();

        //查询两个表的数据
        ApArticle apArticle = this.getById(dto.getArticleId());
        if (apArticle == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }
        LambdaQueryWrapper<ApArticleContent> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ApArticleContent::getArticleId, apArticle.getId());

        ApArticleContent apArticleContent = iApArticleContentService.getOne(queryWrapper);
        if (apArticleContent == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }
        resultMap.put("config",apArticle);
        resultMap.put("content",apArticleContent);

        return ResponseResult.okResult(resultMap);
    }
}
