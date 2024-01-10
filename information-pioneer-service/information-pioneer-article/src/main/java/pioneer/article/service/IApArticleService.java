package pioneer.article.service;

import pioneer.article.dto.ArticleDto;
import pioneer.article.dto.ArticleHomeDto;
import pioneer.article.dto.ArticleInfoDto;
import pioneer.article.entity.ApArticle;
import com.baomidou.mybatisplus.extension.service.IService;
import pioneer.common.dto.ResponseResult;

/**
 * 文章信息表，存储已发布的文章 服务类
 */
public interface IApArticleService extends IService<ApArticle> {

    ResponseResult<Long> saveArticle(ArticleDto dto);

    ResponseResult load(ArticleHomeDto dto,int type);

    ResponseResult loadArticleInfo(ArticleInfoDto dto);
}
