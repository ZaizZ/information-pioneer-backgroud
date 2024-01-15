package pioneer.behavior.service.impl;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import pioneer.behavior.config.ToRedis;
import pioneer.behavior.dto.BehaviorDto;
import pioneer.behavior.entity.ApBehaviorEntry;
import pioneer.behavior.entity.ApFollowBehavior;
import pioneer.behavior.mapper.ApFollowBehaviorMapper;
import pioneer.behavior.service.IApBehaviorEntryService;
import pioneer.behavior.service.IApFollowBehaviorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import pioneer.common.dto.ResponseResult;

/**
 * APP关注行为表 服务实现类
 */
@Service
public class ApFollowBehaviorServiceImpl extends ServiceImpl<ApFollowBehaviorMapper, ApFollowBehavior> implements IApFollowBehaviorService {

    @Autowired
    private IApBehaviorEntryService apBehaviorEntryService;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Override
    @ToRedis
    public ResponseResult saveFollow(BehaviorDto dto) {

        ApBehaviorEntry entryByUserId = apBehaviorEntryService.getEntryByUserId(dto.getUserId());

        ApFollowBehavior apFollowBehavior = new ApFollowBehavior();

        apFollowBehavior.setEntryId(entryByUserId.getId());
        apFollowBehavior.setFollowId(dto.getAuthorId());
        apFollowBehavior.setOperation(dto.getOperation());
        apFollowBehavior.setCreatedTime(new Date());

        mongoTemplate.save(apFollowBehavior);

        return ResponseResult.okResult();
    }
}
