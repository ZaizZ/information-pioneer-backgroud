package pioneer.user.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pioneer.common.dto.ResponseResult;

import pioneer.user.dto.LoginDto;
import pioneer.user.service.IApUserService;


@RestController
@RequestMapping("/api/v1/login")
@Api(tags = "app用户登录")
@CrossOrigin
public class LoginController {


    @Autowired
    private IApUserService apUserService;

    @PostMapping("/login_auth")
    @ApiOperation("app用户登录")
    @ApiModelProperty(name = "dto",dataType = "LoginDto",required = true)
    public ResponseResult login(@RequestBody LoginDto dto){
        return apUserService.login(dto);
    }


}
