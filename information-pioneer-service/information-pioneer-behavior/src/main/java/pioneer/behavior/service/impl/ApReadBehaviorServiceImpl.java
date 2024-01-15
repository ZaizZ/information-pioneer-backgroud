package pioneer.behavior.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import pioneer.behavior.config.ToRedis;
import pioneer.behavior.dto.BehaviorDto;
import pioneer.behavior.entity.ApBehaviorEntry;
import pioneer.behavior.entity.ApReadBehavior;
import pioneer.behavior.mapper.ApReadBehaviorMapper;
import pioneer.behavior.service.IApBehaviorEntryService;
import pioneer.behavior.service.IApReadBehaviorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import pioneer.common.dto.ResponseResult;

import java.util.Date;

/**
 * APP阅读行为表 服务实现类
 */
@Service
public class ApReadBehaviorServiceImpl extends ServiceImpl<ApReadBehaviorMapper, ApReadBehavior> implements IApReadBehaviorService {

    @Autowired
    private IApBehaviorEntryService entryService;
    @Autowired
    private MongoTemplate mongoTemplate;
    @Override
    @ToRedis
    public ResponseResult saveRead(BehaviorDto dto) {
        ApBehaviorEntry entry = entryService.getEntryByUserId(dto.getUserId());

        // 构建阅读行为对象
        ApReadBehavior readBehavior = new ApReadBehavior();

        readBehavior.setEntryId(entry.getId());
        readBehavior.setArticleId(dto.getArticleId());
        readBehavior.setCount(1);
        readBehavior.setReadDuration(dto.getReadDuration());
        readBehavior.setPercentage(dto.getPercentage());
        readBehavior.setLoadDuration(dto.getLoadDuration());
        readBehavior.setCreatedTime(new Date());

        mongoTemplate.save(readBehavior);
        return ResponseResult.okResult();
    }
}
