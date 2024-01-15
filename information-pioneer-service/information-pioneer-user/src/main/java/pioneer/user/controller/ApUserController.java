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
import pioneer.user.entity.ApUser;
import pioneer.user.service.IApUserService;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RestController;

/**
 * APP用户信息表 前端控制器
 */
@RestController
@RequestMapping("/api/v1/user")
@Api(tags = "APP用户信息表接口")
@CrossOrigin
public class ApUserController{

    @Autowired
    private IApUserService apUserService;

    @GetMapping("/{id}")
    public ResponseResult<ApUser> getById(@PathVariable("id") Integer id) {
        ApUser apUser = apUserService.getById(id);
        return ResponseResult.okResult(apUser);
    }

}
