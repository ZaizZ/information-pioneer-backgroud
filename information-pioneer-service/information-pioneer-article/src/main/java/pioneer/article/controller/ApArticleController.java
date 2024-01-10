package pioneer.article.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import pioneer.article.dto.ArticleDto;
import pioneer.article.dto.ArticleHomeDto;
import pioneer.article.dto.ArticleInfoDto;
import pioneer.article.service.IApArticleService;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RestController;
import pioneer.common.dto.ResponseResult;

/**
 * 文章信息表，存储已发布的文章 前端控制器
 */
@RestController
@RequestMapping("/api/v1/article")
@Api(tags = "文章信息表，存储已发布的文章接口")
@CrossOrigin
public class ApArticleController{

    @Autowired
    private IApArticleService apArticleService;

    @PostMapping
    @ApiOperation("新增App文章")
    public ResponseResult<Long> saveArticle(@RequestBody ArticleDto dto){
        return apArticleService.saveArticle(dto);
    }

    @PostMapping("/load")
    @ApiOperation("首次加载文章列表")
    public ResponseResult load(@RequestBody ArticleHomeDto dto){
        return apArticleService.load(dto,1);
    }

    @PostMapping("/loadmore")
    @ApiOperation("加载更多")
    public ResponseResult loadmore(@RequestBody ArticleHomeDto dto){
        return apArticleService.load(dto,2);
    }

    @PostMapping("/loadnew")
    @ApiOperation("加载新的文章")
    public ResponseResult loadnew(@RequestBody ArticleHomeDto dto){
        return apArticleService.load(dto,3);
    }

    @PostMapping("/load_article_info")
    @ApiOperation("根据id查询文章")
    public ResponseResult loadArticleInfo(@RequestBody ArticleInfoDto dto){
        return apArticleService.loadArticleInfo(dto);

    }
}
