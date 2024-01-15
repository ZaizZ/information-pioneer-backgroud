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
import pioneer.behavior.service.IApCollectionBehaviorService;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RestController;
import pioneer.common.dto.ResponseResult;
import pioneer.common.dto.User;
import pioneer.common.enums.AppHttpCodeEnum;
import pioneer.common.util.UserThreadLocalUtil;

/**
 * APP收藏信息表 前端控制器
 */
@RestController
@RequestMapping("/api/v1/collection_behavior")
@Api(tags = "APP收藏信息表接口")
@CrossOrigin
public class ApCollectionBehaviorController{

    @Autowired
    private IApCollectionBehaviorService apCollectionBehaviorService;

    @PostMapping
    @ApiOperation("用户收藏文章行为")
    public ResponseResult saveCollection(@RequestBody BehaviorDto dto) {
        User user = UserThreadLocalUtil.get();
        if(user==null||user.getUserId()==0){
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }
        dto.setUserId(user.getUserId());
        return apCollectionBehaviorService.saveCollection(dto);
    }

}
