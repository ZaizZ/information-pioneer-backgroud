package pioneer.search.service.impl;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import pioneer.common.dto.ResponseResult;
import pioneer.search.dto.ApArticleSearchDto;
import pioneer.search.entity.ApAssociateWords;
import pioneer.search.service.IAssociateService;

import java.util.List;

@Service
public class AssociateServiceImpl implements IAssociateService {

    @Autowired
    private MongoTemplate mongoTemplate;
    @Override
    public ResponseResult searchAssociate(ApArticleSearchDto dto) {
        if(StringUtils.isBlank(dto.getSearchWords())){
            return null;
        }
                                                                    //like查询用正则表达式
        Query query = new Query(Criteria.where("associateWords").regex(dto.getSearchWords()));
        query.limit(dto.getSize());
        List<ApAssociateWords> wordsList = mongoTemplate.find(query, ApAssociateWords.class);

        return ResponseResult.okResult(wordsList);
    }
}
