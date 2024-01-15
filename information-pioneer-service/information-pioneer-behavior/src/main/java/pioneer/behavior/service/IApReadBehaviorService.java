package pioneer.behavior.service;

import pioneer.behavior.dto.BehaviorDto;
import pioneer.behavior.entity.ApReadBehavior;
import com.baomidou.mybatisplus.extension.service.IService;
import pioneer.common.dto.ResponseResult;

/**
 * APP阅读行为表 服务类
 */
public interface IApReadBehaviorService extends IService<ApReadBehavior> {

    ResponseResult saveRead(BehaviorDto dto);
}
