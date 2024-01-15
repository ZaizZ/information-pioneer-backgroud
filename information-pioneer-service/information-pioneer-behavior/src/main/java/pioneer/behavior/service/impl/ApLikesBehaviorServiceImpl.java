package pioneer.behavior.service.impl;
import java.util.Date;
import java.util.HashMap;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import pioneer.behavior.config.ToRedis;
import pioneer.behavior.dto.BehaviorDto;
import pioneer.behavior.entity.ApBehaviorEntry;
import pioneer.behavior.entity.ApLikesBehavior;
import pioneer.behavior.mapper.ApLikesBehaviorMapper;
import pioneer.behavior.service.IApBehaviorEntryService;
import pioneer.behavior.service.IApLikesBehaviorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import pioneer.common.dto.ResponseResult;

/**
 * APP点赞行为表 服务实现类
 */
@Service
public class ApLikesBehaviorServiceImpl extends ServiceImpl<ApLikesBehaviorMapper, ApLikesBehavior> implements IApLikesBehaviorService {

    @Autowired
    private IApBehaviorEntryService apBehaviorEntryService;

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Value("${topic.articleLikes}")
    private String topic;

    @Autowired
    private MongoTemplate mongoTemplate;
    @Override
    @ToRedis
    public ResponseResult saveLikes(BehaviorDto dto) {

        ApBehaviorEntry entryByUserId = apBehaviorEntryService.getEntryByUserId(dto.getUserId());

        ApLikesBehavior apLikesBehavior = new ApLikesBehavior();
        apLikesBehavior.setEntryId(entryByUserId.getId());
        apLikesBehavior.setArticleId(dto.getArticleId());
        apLikesBehavior.setType(0);
        apLikesBehavior.setOperation(dto.getOperation());
        apLikesBehavior.setCreatedTime(new Date());

        mongoTemplate.save(apLikesBehavior);

        //异步发送到kafka更新文章的点赞数
        HashMap map = new HashMap<>();
        map.put("articleId",dto.getArticleId());
        if (dto.getOperation()==0){
            map.put("likes",1);
        }else {
            map.put("likes",-1);
        }
        kafkaTemplate.send(topic, JSON.toJSONString(map));
        return ResponseResult.okResult();
    }
}
