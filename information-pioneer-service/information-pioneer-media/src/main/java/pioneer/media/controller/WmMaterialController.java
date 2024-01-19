package pioneer.media.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pioneer.common.dto.ResponseResult;
import pioneer.media.dto.WmMaterialDto;
import pioneer.media.entity.WmMaterial;
import pioneer.media.service.IWmMaterialService;

import org.springframework.web.bind.annotation.RestController;

/**
 * 自媒体图文素材信息表 前端控制器
 */
@RestController
@RequestMapping("/api/v1/material")
@Api(tags = "自媒体图文素材信息表接口")
@CrossOrigin
public class WmMaterialController{

    @Autowired
    private IWmMaterialService wmMaterialService;

    @PostMapping("/upload_picture")
    @ApiOperation("图片上传")
    public ResponseResult<WmMaterial> upload(MultipartFile file){
        return wmMaterialService.saveWmMaterial(file);
    }

    @PostMapping("/list")
    @ApiOperation("显示素材列表")
    public ResponseResult listByStatus(@RequestBody WmMaterialDto dto){
        return wmMaterialService.listByStatus(dto);
    }

    @PutMapping("/collect/{id}")
    @ApiOperation("收藏文章")
    public ResponseResult collection(@PathVariable Integer id){
        return wmMaterialService.collection(id,1);
    }

    @PutMapping("/cancel_collect/{id}")
    @ApiOperation("取消收藏文章")
    public ResponseResult cancelCollection(@PathVariable Integer id){
        return wmMaterialService.collection(id,2);
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除素材")
    public ResponseResult deleteMaterial(@PathVariable Integer id){
        return wmMaterialService.deleteMaterial(id);
    }
}
