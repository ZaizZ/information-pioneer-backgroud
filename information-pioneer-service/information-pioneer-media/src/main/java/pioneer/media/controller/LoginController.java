package pioneer.media.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import pioneer.common.dto.ResponseResult;
import pioneer.media.dto.LoginDto;
import pioneer.media.service.IWmUserService;


@RestController
@RequestMapping("/login")
@Api(tags = "自媒体人登录")
@CrossOrigin
public class LoginController {


    @Autowired
    private IWmUserService iWmUserService;

    @PostMapping("/in")
    @ApiOperation("自媒体人登录")
    @ApiModelProperty(name = "dto",dataType = "LoginDto",required = true)
    public ResponseResult login(@RequestBody LoginDto dto){
        return iWmUserService.login(dto);
    }


}
