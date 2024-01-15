package pioneer.behavior.service.impl;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import pioneer.behavior.config.ToRedis;
import pioneer.behavior.dto.BehaviorDto;
import pioneer.behavior.entity.ApBehaviorEntry;
import pioneer.behavior.entity.ApCollectionBehavior;
import pioneer.behavior.mapper.ApCollectionBehaviorMapper;
import pioneer.behavior.service.IApBehaviorEntryService;
import pioneer.behavior.service.IApCollectionBehaviorService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import pioneer.common.dto.ResponseResult;

import java.util.Date;
import java.util.HashMap;

/**
 * APP收藏信息表 服务实现类
 */
@Service
public class ApCollectionBehaviorServiceImpl extends ServiceImpl<ApCollectionBehaviorMapper, ApCollectionBehavior> implements IApCollectionBehaviorService {

    @Autowired
    private IApBehaviorEntryService apBehaviorEntryService;
    @Autowired
    private KafkaTemplate kafkaTemplate;
    @Value("${topic.articleCollections}")
    private String topic;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    @ToRedis
    public ResponseResult saveCollection(BehaviorDto dto) {
        //        更好用户ID或设备id获取操作实体
        ApBehaviorEntry entry = apBehaviorEntryService.getEntryByUserId(dto.getUserId());

        ApCollectionBehavior apCollectionBehavior = new ApCollectionBehavior();
        apCollectionBehavior.setEntryId(entry.getId());
        apCollectionBehavior.setOperation(dto.getOperation());
        apCollectionBehavior.setArticleId(dto.getArticleId());
        apCollectionBehavior.setCollectionTime(new Date());

        mongoTemplate.save(apCollectionBehavior);


        HashMap map = new HashMap();
        map.put("articleId", dto.getArticleId());
        if (dto.getOperation()==0){
            map.put("collections",1);
        }else {
            map.put("collections",-1);
        }
        kafkaTemplate.send(topic, JSON.toJSONString(map));
        return ResponseResult.okResult();
    }
}
