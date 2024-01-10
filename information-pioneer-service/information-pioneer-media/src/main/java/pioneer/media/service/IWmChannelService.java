package pioneer.media.service;

import com.baomidou.mybatisplus.extension.service.IService;
import pioneer.common.dto.ResponseResult;
import pioneer.media.dto.ChannelDto;
import pioneer.media.entity.WmChannel;

import java.util.List;

/**
 * 频道信息表 服务类
 */
public interface IWmChannelService extends IService<WmChannel> {

    ResponseResult listByName(ChannelDto dto);

    ResponseResult saveChannel(WmChannel entity);

    ResponseResult<List<WmChannel>> selectChannels();

}
