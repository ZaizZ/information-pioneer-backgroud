package pioneer.behavior.controller;

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
import pioneer.behavior.dto.BehaviorDto;
import pioneer.behavior.service.IApLikesBehaviorService;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RestController;
import pioneer.common.dto.ResponseResult;
import pioneer.common.dto.User;
import pioneer.common.enums.AppHttpCodeEnum;
import pioneer.common.util.UserThreadLocalUtil;

/**
 * APP点赞行为表 前端控制器
 */
@RestController
@RequestMapping("/api/v1/likes_behavior")
@Api(tags = "APP点赞行为表接口")
@CrossOrigin
public class ApLikesBehaviorController{

    @Autowired
    private IApLikesBehaviorService apLikesBehaviorService;

    @PostMapping
    @ApiOperation("用户点赞文章行为")
    public ResponseResult saveLikes(@RequestBody BehaviorDto dto) {
        User user = UserThreadLocalUtil.get();
        if(user==null||user.getUserId()==0){
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }
        dto.setUserId(user.getUserId());
        return apLikesBehaviorService.saveLikes(dto);
    }
}
