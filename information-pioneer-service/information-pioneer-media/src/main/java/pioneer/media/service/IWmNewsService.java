package pioneer.media.service;

import pioneer.common.dto.PageResponseResult;
import pioneer.common.dto.ResponseResult;
import pioneer.media.dto.NewsAuthDto;
import pioneer.media.dto.WmNewsDto;
import pioneer.media.dto.WmNewsPageDto;
import pioneer.media.entity.WmNews;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 自媒体图文内容信息表 服务类
 */
public interface IWmNewsService extends IService<WmNews> {

    ResponseResult listByCondition(WmNewsPageDto dto);

    ResponseResult submit(WmNewsDto dto);

    ResponseResult<List<WmNews>> findPageByName(NewsAuthDto dto);

    ResponseResult findNewsVoById(Integer id);

    ResponseResult auth(NewsAuthDto dto, int type);

    ResponseResult downOrUp(WmNewsDto dto);
}
