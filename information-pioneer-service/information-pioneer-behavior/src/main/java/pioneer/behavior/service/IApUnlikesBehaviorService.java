package pioneer.behavior.service;

import pioneer.behavior.dto.BehaviorDto;
import pioneer.behavior.entity.ApUnlikesBehavior;
import com.baomidou.mybatisplus.extension.service.IService;
import pioneer.common.dto.ResponseResult;

/**
 * APP不喜欢行为表 服务类
 */
public interface IApUnlikesBehaviorService extends IService<ApUnlikesBehavior> {

    ResponseResult saveUnlikes(BehaviorDto dto);
}
