package pioneer.article.task;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import pioneer.article.entity.ApArticle;
import pioneer.article.entity.ApArticleContent;
import pioneer.article.service.IApArticleContentService;
import pioneer.article.service.IApArticleService;
import pioneer.common.minio.MinIOService;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

@Component
public class CreateHtmlTask {

    @Autowired
    private IApArticleContentService apArticleContentService;
    @Autowired
    private IApArticleService apArticleService;

    @Autowired
    private Configuration configuration;
    @Autowired
    private MinIOService minIOService;

    @Async("asyncExecutor")
    public void createHtml(ApArticle apArticle,String content) {
        try {
            Long articleId = apArticle.getId();

            List<Map> mapList = JSON.parseArray(content, Map.class);

            Map dataModel = new HashMap();
            dataModel.put("content",mapList); //content要与模板中的名字相同

//        获取模板
            Template template = configuration.getTemplate("article.ftl");

//       数据和模板结合

            StringWriter writer = new StringWriter();
            template.process(dataModel,writer);

//        把生成的html放到minio上
            InputStream inputStream = new ByteArrayInputStream(writer.toString().getBytes());
            minIOService.upload("/article/"+articleId+".html",inputStream,"text/html")  ;// String name, InputStream inputStream, String contentType


//        http://192.168.181.132:9000/pioneer

//        ApArticle apArticle = apArticleService.getById(articleId);
            apArticle.setStaticUrl("http://192.168.181.132:9000/pioneer/article/"+articleId+".html");
            apArticleService.updateById(apArticle);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}

