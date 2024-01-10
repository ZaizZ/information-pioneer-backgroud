package pioneer.admin.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pioneer.admin.dto.LoginDto;
import pioneer.admin.service.IAdUserService;
import pioneer.common.dto.ResponseResult;


@RestController
@RequestMapping("/login")
@Api(tags = "管理员登录")
@CrossOrigin
public class LoginController {


    @Autowired
    private IAdUserService adUserService;

    @PostMapping("/in")
    @ApiOperation("管理员登录")
    @ApiModelProperty(name = "dto",dataType = "LoginDto",required = true)
    public ResponseResult login(@RequestBody LoginDto dto){
        return adUserService.login(dto);
    }


}
