package pioneer.behavior.service;

import pioneer.behavior.dto.BehaviorDto;
import pioneer.behavior.entity.ApCollectionBehavior;
import com.baomidou.mybatisplus.extension.service.IService;
import pioneer.common.dto.ResponseResult;

/**
 * APP收藏信息表 服务类
 */
public interface IApCollectionBehaviorService extends IService<ApCollectionBehavior> {

    ResponseResult saveCollection(BehaviorDto dto);
}
