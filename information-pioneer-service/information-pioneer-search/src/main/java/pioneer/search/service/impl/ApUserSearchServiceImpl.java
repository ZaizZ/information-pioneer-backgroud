package pioneer.search.service.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import pioneer.common.dto.ResponseResult;
import pioneer.common.dto.User;
import pioneer.common.util.UserThreadLocalUtil;
import pioneer.search.dto.ApArticleSearchDto;
import pioneer.search.entity.ApUserSearch;
import pioneer.search.service.IApUserSearchService;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApUserSearchServiceImpl implements IApUserSearchService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    @Async
    public void saveUserSearch(ApArticleSearchDto dto, User user) {
        System.out.println("保存搜索记录当前线程的名称："+Thread.currentThread().getName());
        // 判断当前用户查询的关键词是否已经有记录
        Query query = new Query();
        Criteria criteria = Criteria.where("keyword").is(dto.getSearchWords());
        if (user==null) {
            //用户为空就根据设备id来查看是否已经有记录
            criteria.and("equipmentId").is(dto.getEquipmentId());
        }else{
            criteria.and("userId").is(user.getUserId());
        }
        query.addCriteria(criteria);

        ApUserSearch userSearch = mongoTemplate.findOne(query, ApUserSearch.class);
        if(userSearch==null){
            userSearch = new ApUserSearch();
            userSearch.setUserId(user.getUserId());
            userSearch.setEquipmentId(dto.getEquipmentId());
            userSearch.setKeyword(dto.getSearchWords());
            userSearch.setCreatedTime(new Date());
            mongoTemplate.save(userSearch);
        }


    }

    @Override
    public ResponseResult loadHistory(ApArticleSearchDto dto) {
        User user = UserThreadLocalUtil.get();

        Query query = new Query();
        Criteria criteria = new Criteria();
        if (user==null) {
            criteria.and("equipmentId").is(dto.getEquipmentId());
        }else{
            criteria.and("userId").is(user.getUserId());
        }
        query.addCriteria(criteria);

        query.with(Sort.by(Sort.Direction.DESC,"createdTime"));
        query.limit(5);
//        展示用户的搜索记录5条
        List<ApUserSearch> apUserSearchList = mongoTemplate.find(query, ApUserSearch.class);

        return ResponseResult.okResult(apUserSearchList);
    }

    @Override
    public ResponseResult delHistory(ApArticleSearchDto dto) {
        List<String> idList = dto.getHisList().stream().map(his -> {
            return his.getId();
        }).collect(Collectors.toList());

        Query query = new Query(Criteria.where("_id").in(idList));
        mongoTemplate.remove(query,ApUserSearch.class);

        return ResponseResult.okResult();
    }
}
