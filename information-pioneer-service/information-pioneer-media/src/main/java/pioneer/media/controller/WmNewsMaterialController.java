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
import pioneer.media.service.IWmNewsMaterialService;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RestController;

/**
 * 自媒体图文引用素材信息表 前端控制器
 */
@RestController
@RequestMapping("/api/v1/news_material")
@Api(tags = "自媒体图文引用素材信息表接口")
@CrossOrigin
public class WmNewsMaterialController{

    @Autowired
    private IWmNewsMaterialService wmNewsMaterialService;



}
