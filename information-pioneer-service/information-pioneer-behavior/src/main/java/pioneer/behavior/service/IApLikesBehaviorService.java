package pioneer.behavior.service;

import pioneer.behavior.dto.BehaviorDto;
import pioneer.behavior.entity.ApLikesBehavior;
import com.baomidou.mybatisplus.extension.service.IService;
import pioneer.common.dto.ResponseResult;

/**
 * APP点赞行为表 服务类
 */
public interface IApLikesBehaviorService extends IService<ApLikesBehavior> {

    ResponseResult saveLikes(BehaviorDto dto);
}
