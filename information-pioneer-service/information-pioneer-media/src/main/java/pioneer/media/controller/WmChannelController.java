package pioneer.media.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pioneer.common.dto.ResponseResult;
import pioneer.media.dto.ChannelDto;
import pioneer.media.entity.WmChannel;
import pioneer.media.service.IWmChannelService;

import java.util.List;

/**
 * 频道信息表 前端控制器
 */
@RestController
@RequestMapping("/api/v1/channel")
@Api(tags = "频道管理")
public class WmChannelController {

    @Autowired
    private IWmChannelService wmChannelService;

    /**
     * 根据名称模糊查询分页列表
     * @param dto
     * @return
     */
    @PostMapping("/list")
    @ApiOperation(value = "根据名称模糊查询分页列表")  // value指名称
    @ApiImplicitParam(name = "dto", value = "查询对象", required = true, dataType = "ChannelDto")
    public ResponseResult listByName(@RequestBody ChannelDto dto){
        return wmChannelService.listByName(dto);
    }

    /**
     * 保存频道
     * @param entity
     * @return
     */
    @PostMapping
    @ApiOperation("新增频道")
    public ResponseResult saveChannel(@RequestBody WmChannel entity){
        return wmChannelService.saveChannel(entity);
    }

    @GetMapping("/channels")
    @ApiOperation("查询所有频道")
    public ResponseResult selectChannels(){
        return wmChannelService.selectChannels();
    }

}
