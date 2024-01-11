package pioneer.media.listener;

import org.redisson.api.RBlockingDeque;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pioneer.media.entity.WmNews;
import pioneer.media.service.IAuditService;
import pioneer.media.service.IWmNewsService;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class PublishArticleListener {

    @Autowired
    private RedissonClient redissonClient;
    @Autowired
    private IWmNewsService wmNewsService;

    @Autowired
    private IAuditService auditService;

    @PostConstruct //类初始化好后调用
    public void publisArticle(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                RBlockingDeque<Object> blockingDeque = redissonClient.getBlockingDeque("publish-task");

                while (true) {
                    try {
                        //30秒内没有任何元素可供获取，那么 poll 方法会返回 null
                        Integer wmNewsId = (Integer)blockingDeque.poll(30, TimeUnit.SECONDS);
                        if(wmNewsId==null){
                            continue;
                        }
                        WmNews wmNews = wmNewsService.getById(wmNewsId);
                        if (wmNews==null){
                            continue;
                        }

                        //远程调用新增app文章
                        Long articleId = auditService.createApArticle(wmNews);
                        //审核通过，已经发布到App了
                        wmNews.setArticleId(articleId);
                        wmNews.setStatus(9);
                        wmNews.setSubmitedTime(new Date());
                        wmNewsService.updateById(wmNews);

                    } catch (InterruptedException e) {
                        continue;
                    }
                }

            }
        }).start();
    }
}
