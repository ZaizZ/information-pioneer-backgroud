package pioneer.search.controller;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import pioneer.common.dto.ResponseResult;
import pioneer.search.dto.ApArticleSearchDto;
import pioneer.search.service.IAssociateService;


@RestController
@RequestMapping("/api/v1/associate")
@Api(tags = "联想词表接口")
@CrossOrigin
public class AssociateController {

    @Autowired
    private IAssociateService associateService;

    /**
     * 查询关联词
     */
    @PostMapping("/search")
    @ApiOperation("联想查询")
    public ResponseResult search(@RequestBody ApArticleSearchDto dto) {
        return associateService.searchAssociate(dto);
    }

}
