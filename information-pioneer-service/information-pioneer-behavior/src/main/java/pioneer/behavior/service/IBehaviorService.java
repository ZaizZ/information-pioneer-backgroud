package pioneer.behavior.service;

import pioneer.behavior.dto.BehaviorDto;
import pioneer.common.dto.ResponseResult;

public interface IBehaviorService {
    ResponseResult loadBehavior(BehaviorDto dto);
}
