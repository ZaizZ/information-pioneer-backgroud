package pioneer.media.service;

import pioneer.common.dto.ResponseResult;
import pioneer.media.dto.WmNewsDto;
import pioneer.media.dto.WmNewsPageDto;
import pioneer.media.entity.WmNews;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 自媒体图文内容信息表 服务类
 */
public interface IWmNewsService extends IService<WmNews> {

    ResponseResult listByCondition(WmNewsPageDto dto);

    ResponseResult submit(WmNewsDto dto);
}
