package pioneer.media.controller;

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
import pioneer.media.entity.WmUser;
import pioneer.media.service.IWmUserService;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RestController;

/**
 * 自媒体用户信息表 前端控制器
 */
@RestController
@RequestMapping("/api/v1/user")
@Api(tags = "自媒体用户信息表接口")
@CrossOrigin
public class WmUserController{

    @Autowired
    private IWmUserService wmUserService;

    @PostMapping
    @ApiOperation("保存自媒体用户")
    public ResponseResult<WmUser> saveWmUser(@RequestBody WmUser wmUser){
        return wmUserService.saveWmUser(wmUser);
    }

    @PutMapping
    @ApiOperation("更新自媒体用户")
    public ResponseResult updateWmUser(@RequestBody WmUser wmUser){
         wmUserService.updateById(wmUser);
         return ResponseResult.okResult();
    }

}
