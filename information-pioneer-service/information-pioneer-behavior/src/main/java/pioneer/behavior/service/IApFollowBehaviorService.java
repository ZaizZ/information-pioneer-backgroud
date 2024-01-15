package pioneer.behavior.service;

import pioneer.behavior.dto.BehaviorDto;
import pioneer.behavior.entity.ApFollowBehavior;
import com.baomidou.mybatisplus.extension.service.IService;
import pioneer.common.dto.ResponseResult;

/**
 * APP关注行为表 服务类
 */
public interface IApFollowBehaviorService extends IService<ApFollowBehavior> {

    ResponseResult saveFollow(BehaviorDto dto);
}
