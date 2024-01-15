package pioneer.behavior.service.impl;
import java.util.Date;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import pioneer.behavior.entity.ApBehaviorEntry;
import pioneer.behavior.mapper.ApBehaviorEntryMapper;
import pioneer.behavior.service.IApBehaviorEntryService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * APP行为实体表,一个行为实体可能是用户或者设备，或者其它 服务实现类
 */
@Service
public class ApBehaviorEntryServiceImpl extends ServiceImpl<ApBehaviorEntryMapper, ApBehaviorEntry> implements IApBehaviorEntryService {

    @Override
    public ApBehaviorEntry getEntryByUserId(Integer userId) {
        LambdaQueryWrapper<ApBehaviorEntry> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ApBehaviorEntry::getEntryId,userId.toString());
        ApBehaviorEntry apBehaviorEntry = this.getOne(queryWrapper);
        //如果不存在，则新增一条记录
        if (apBehaviorEntry == null){
            apBehaviorEntry = new ApBehaviorEntry();
            apBehaviorEntry.setType(1);
            apBehaviorEntry.setEntryId(userId.toString());
            apBehaviorEntry.setCreatedTime(new Date());
            this.save(apBehaviorEntry);
        }
        return apBehaviorEntry;
    }
}
