package pioneer.article.dto;


import lombok.Data;
import lombok.EqualsAndHashCode;
import pioneer.article.entity.ApArticle;

@Data
@EqualsAndHashCode(callSuper = true)
public class ArticleDto extends ApArticle {
    /**
     * 文章内容
     */
    private String content;
}
