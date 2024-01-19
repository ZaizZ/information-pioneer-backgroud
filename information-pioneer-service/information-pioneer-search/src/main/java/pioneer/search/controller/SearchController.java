package pioneer.search.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pioneer.common.dto.ResponseResult;
import pioneer.search.dto.ApArticleSearchDto;
import pioneer.search.service.IArticleSearchService;

@RestController
@RequestMapping("/api/v1/article/search")
@Api("app搜索")
public class SearchController {

    @Autowired
    private IArticleSearchService articleSearchService;

    /**
     * 页面查询文章列表
     */
    @PostMapping("/search")
    @ApiOperation("搜索新闻")
    public ResponseResult search(@RequestBody ApArticleSearchDto dto){
        return articleSearchService.search(dto);
    }
}
