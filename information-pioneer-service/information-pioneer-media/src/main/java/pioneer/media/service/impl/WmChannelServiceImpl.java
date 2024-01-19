package pioneer.media.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import pioneer.common.dto.PageResponseResult;
import pioneer.common.dto.ResponseResult;
import pioneer.common.enums.AppHttpCodeEnum;
import pioneer.media.dto.ChannelDto;
import pioneer.media.entity.WmChannel;
import pioneer.media.mapper.WmChannelMapper;
import pioneer.media.service.IWmChannelService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 频道信息表 服务实现类
 */
@Service
public class WmChannelServiceImpl extends ServiceImpl<WmChannelMapper, WmChannel> implements IWmChannelService {


    @Override
    public ResponseResult<WmChannel> listByName(ChannelDto dto) {
        //根据名称分页查询
        if (dto == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);
        }
        //使用mybatisPlus推荐的lambda表达式的写法
        LambdaQueryWrapper<WmChannel> queryWrapper = new LambdaQueryWrapper<>();
        //LambdaQueryWrapper<WmChannel> queryWrapper = Wrappers.lambdaQuery();
        if (StringUtils.isNotBlank(dto.getName())){
            //构建name模糊查询
            queryWrapper.like(WmChannel::getName,dto.getName());
        }
        //构建分页对象
        Page<WmChannel> page = new Page<>(dto.getPage(), dto.getSize());
        //返回查询结果
        Page<WmChannel> page1 = this.page(page, queryWrapper);
        //构建分页对象
        return  new PageResponseResult(dto.getPage(), dto.getSize(),page1.getTotal(),page1.getRecords());
    }

    @Override
    public ResponseResult saveChannel(WmChannel wmChannel) {
        if (StringUtils.isBlank(wmChannel.getName())){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_REQUIRE);
        }
        //频道名称不能重复
        //新增之前应该根据名称查询
        //如果能查询出结果，返回数据已存在
        //如果没有查询出结果，在进行新增
        LambdaQueryWrapper<WmChannel> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WmChannel::getName,wmChannel.getName());
        WmChannel one = this.getOne(queryWrapper);
        if (one!=null){
            //如果能查询出结果，表示数据已存在，返回数据已存在
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_EXIST);
        }else {
            wmChannel.setCreatedTime(new Date());
            this.save(wmChannel);
        }
        //返回操作成功
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult selectChannels() {
        LambdaQueryWrapper<WmChannel> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(WmChannel::getStatus,true);
        List<WmChannel> list = this.list(queryWrapper);
        return ResponseResult.okResult(list);
    }
}
