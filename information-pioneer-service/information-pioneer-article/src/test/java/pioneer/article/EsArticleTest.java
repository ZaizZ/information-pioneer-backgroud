package pioneer.article;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pioneer.article.entity.ApArticle;
import pioneer.article.entity.ApArticleSearch;
import pioneer.article.es.ArticleRepository;
import pioneer.article.service.IApArticleService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
public class EsArticleTest {

    @Autowired
    private IApArticleService apArticleService;
    @Autowired
    private ArticleRepository articleRepository;
    @Test
    public void initEs(){

        //数据预热，将需要的数据存入es
        LambdaQueryWrapper<ApArticle> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(ApArticle::getIsDown,false);
        queryWrapper.eq(ApArticle::getIsDelete,false);
        List<ApArticle> list = apArticleService.list(queryWrapper);

        List<ApArticleSearch> articleSearchList = list.stream().map(apArticle -> {
            ApArticleSearch articleSearch = new ApArticleSearch();
            BeanUtils.copyProperties(apArticle, articleSearch);
            return articleSearch;
        }).collect(Collectors.toList());

        articleRepository.saveAll(articleSearchList);

    }


}
