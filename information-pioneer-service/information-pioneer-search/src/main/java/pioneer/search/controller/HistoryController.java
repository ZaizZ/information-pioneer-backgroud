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
import pioneer.search.service.IApUserSearchService;

@RestController
@RequestMapping("/api/v1/history")
@Api("历史搜索记录接口")
public class HistoryController {

    @Autowired
    private IApUserSearchService userSearchService;


    @PostMapping("/load")
    @ApiOperation("加载历史搜索记录")
    public ResponseResult loadHistory(@RequestBody ApArticleSearchDto dto){

        return userSearchService.loadHistory(dto);
    }

    @PostMapping("/del")
    @ApiOperation("删除历史搜索记录")
    public ResponseResult delHistory(@RequestBody ApArticleSearchDto dto){
        return userSearchService.delHistory(dto);
    }
}
