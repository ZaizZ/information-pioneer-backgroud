package pioneer.media.controller;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import pioneer.common.dto.PageResponseResult;
import pioneer.common.dto.ResponseResult;
import pioneer.common.enums.AppHttpCodeEnum;
import pioneer.media.dto.NewsAuthDto;
import pioneer.media.dto.WmNewsDto;
import pioneer.media.dto.WmNewsPageDto;
import pioneer.media.entity.WmNews;
import pioneer.media.service.IWmNewsService;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RestController;

/**
 * 自媒体图文内容信息表 前端控制器
 */
@RestController
@RequestMapping("/api/v1/news")
@Api(tags = "自媒体图文内容信息表接口")
@CrossOrigin
public class WmNewsController{

    @Autowired
    private IWmNewsService wmNewsService;

    @PostMapping("/list")
    @ApiOperation("文章列表查询")
    public ResponseResult listByCondition(@RequestBody WmNewsPageDto dto){
        return wmNewsService.listByCondition(dto);
    }

    @GetMapping("/{id}")
    @ApiOperation("根据id查询自媒体人文章")
    public ResponseResult getById(@PathVariable Integer id){
        WmNews news = wmNewsService.getById(id);
        if (news==null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }
        return ResponseResult.okResult(news);
    }

    @PostMapping("/submit")
    @ApiOperation("发布自媒体文章")
    public ResponseResult submit(@RequestBody WmNewsDto dto){
        return wmNewsService.submit(dto);
    }

    @PostMapping("/findPageByName")
    @ApiOperation("文章列表展示")
    public ResponseResult<List<WmNews>> findPageByName(@RequestBody NewsAuthDto dto) {
        return wmNewsService.findPageByName(dto);
    }

    @GetMapping("/findNewsVoById/{id}")
    @ApiOperation("根据id展示文章详情")
    public ResponseResult findNewsVoById(@PathVariable Integer id) {
        return wmNewsService.findNewsVoById(id);
    }

    @PostMapping("/auth_pass")
    @ApiOperation("人工审核成功")
    public ResponseResult auth_pass(@RequestBody NewsAuthDto dto) {
        return wmNewsService.auth(dto,1);
    }

    @PostMapping("/auth_fail")
    @ApiOperation("人工审核失败")
    public ResponseResult auth_fail(@RequestBody NewsAuthDto dto) {
        return wmNewsService.auth(dto,2);
    }

    @PutMapping("/down_or_up")
    @ApiOperation("自媒体文章上下架")
    public ResponseResult downOrUp(@RequestBody WmNewsDto dto){
        return wmNewsService.downOrUp(dto);
    }

}
