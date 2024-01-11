package pioneer.article;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import pioneer.article.entity.ApArticle;
import pioneer.article.entity.ApArticleContent;
import pioneer.article.service.IApArticleContentService;
import pioneer.article.service.IApArticleService;
import pioneer.common.minio.MinIOService;
import freemarker.template.Configuration;
import freemarker.template.Template;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
public class FreemarkerTest {
    @Autowired
    private IApArticleContentService apArticleContentService;
    @Autowired
    private IApArticleService apArticleService;

    @Autowired
    private Configuration configuration;
    @Autowired
    private MinIOService minIOService;


    public void createHtml(ApArticle apArticle) throws Exception{
        Long articleId = apArticle.getId();
//        准备数据
        LambdaQueryWrapper<ApArticleContent> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ApArticleContent::getArticleId,articleId);
        ApArticleContent apArticleContent = apArticleContentService.getOne(queryWrapper);

        if(apArticleContent==null){
            return;
        }

        String content = apArticleContent.getContent();
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


    }


    @Test
    public void createAllHtml() throws Exception{
//       查询所有未下架未删除的   生成静态页面的文章

        LambdaQueryWrapper<ApArticle> queryWrapper = new LambdaQueryWrapper();
        queryWrapper.eq(ApArticle::getIsDelete,false);
        queryWrapper.eq(ApArticle::getIsDown,false);
        List<ApArticle> list = apArticleService.list(queryWrapper);

        for (ApArticle apArticle : list) {
           createHtml(apArticle);
        }


    }



}
