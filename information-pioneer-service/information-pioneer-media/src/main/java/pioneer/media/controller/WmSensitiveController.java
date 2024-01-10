package pioneer.media.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.listener.PageReadListener;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.web.multipart.MultipartFile;
import pioneer.common.dto.ResponseResult;
import pioneer.media.entity.WmSensitive;
import pioneer.media.service.IWmSensitiveService;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * 敏感词信息表 前端控制器
 */
@RestController
@RequestMapping("/api/v1/sensitive")
@Api(tags = "敏感词信息表接口")
@CrossOrigin
@Slf4j
public class WmSensitiveController{

    @Autowired
    private IWmSensitiveService wmSensitiveService;

    @PostMapping("/upload")
    @ApiOperation("导入敏感词")
    public ResponseResult upload(MultipartFile file) throws IOException {

        return wmSensitiveService.upload(file);
    }

    @PostMapping("/download")
    @ApiOperation("导出敏感词")
    public ResponseResult download(HttpServletResponse response) throws Exception {
        return wmSensitiveService.download(response);
    }

}
