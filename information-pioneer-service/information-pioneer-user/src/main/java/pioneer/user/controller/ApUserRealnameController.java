package pioneer.user.controller;

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
import pioneer.common.dto.ResponseResult;
import pioneer.user.dto.AuthDto;
import pioneer.user.service.IApUserRealnameService;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RestController;

/**
 * APP实名认证信息表 前端控制器
 */
@RestController
@RequestMapping("/api/v1/user_realname")
@Api(tags = "APP实名认证信息表接口")
@CrossOrigin
public class ApUserRealnameController{

    @Autowired
    private IApUserRealnameService apUserRealnameService;

    @PostMapping("/list")
    @ApiOperation("查询实名认证列表")
    public ResponseResult listByStatus(@RequestBody AuthDto dto){
        return apUserRealnameService.listByStatus(dto);
    }

    @PostMapping("/authPass")
    @ApiOperation("通过")
    public ResponseResult authPass(@RequestBody AuthDto dto){
        return apUserRealnameService.auth(dto,1);
    }

    @PostMapping("/authFail")
    @ApiOperation("驳回")
    public ResponseResult authFail(@RequestBody AuthDto dto){
        return apUserRealnameService.auth(dto,0);
    }


}
