package pioneer.article.listener;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import pioneer.article.entity.ApArticle;
import pioneer.article.service.IApArticleService;

import java.util.Map;

@Component
@Slf4j
public class CommentListener {

    @Autowired
    private IApArticleService apArticleService;

    @KafkaListener(topics = "${topic.comment}")
    public void receiveMsg(ConsumerRecord<String,String> msg){
        String value = msg.value();
        if (StringUtils.isNotBlank(value)){
            Map map = JSON.parseObject(value, Map.class);
            Long articleId = (Long) map.get("articleId");
            Integer comment = (Integer) map.get("comment");

            ApArticle apArticle = apArticleService.getById(articleId);
            if (apArticle!=null){
                apArticle.setComment(apArticle.getComment()+comment);
                apArticleService.updateById(apArticle);
            }
        }
    }
}
