package pioneer.behavior.service.impl;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import pioneer.behavior.config.ToRedis;
import pioneer.behavior.dto.BehaviorDto;
import pioneer.behavior.entity.ApBehaviorEntry;
import pioneer.behavior.entity.ApUnlikesBehavior;
import pioneer.behavior.mapper.ApUnlikesBehaviorMapper;
import pioneer.behavior.service.IApBehaviorEntryService;
import pioneer.behavior.service.IApUnlikesBehaviorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import pioneer.common.dto.ResponseResult;

/**
 * APP不喜欢行为表 服务实现类
 */
@Service
public class ApUnlikesBehaviorServiceImpl extends ServiceImpl<ApUnlikesBehaviorMapper, ApUnlikesBehavior> implements IApUnlikesBehaviorService {

    @Autowired
    private IApBehaviorEntryService apBehaviorEntryService;

    @Autowired
    private MongoTemplate mongoTemplate;
    @Override
    @ToRedis
    public ResponseResult saveUnlikes(BehaviorDto dto) {

        ApBehaviorEntry entryByUserId = apBehaviorEntryService.getEntryByUserId(dto.getUserId());

        ApUnlikesBehavior apUnlikesBehavior = new ApUnlikesBehavior();
        apUnlikesBehavior.setEntryId(entryByUserId.getId());
        apUnlikesBehavior.setArticleId(dto.getArticleId());
        apUnlikesBehavior.setOperation(dto.getOperation());
        apUnlikesBehavior.setCreatedTime(new Date());

        mongoTemplate.save(apUnlikesBehavior);
        return ResponseResult.okResult();
    }
}
