package pioneer.behavior.service;

import pioneer.behavior.entity.ApBehaviorEntry;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * APP行为实体表,一个行为实体可能是用户或者设备，或者其它 服务类
 */
public interface IApBehaviorEntryService extends IService<ApBehaviorEntry> {

    ApBehaviorEntry getEntryByUserId(Integer userId);
}
