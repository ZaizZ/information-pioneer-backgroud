package pioneer.article.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.io.Serializable;
import java.util.Date;

@Data
@Document(indexName = "app_info_article",type = "_doc",shards = 3,replicas = 1)
public class ApArticleSearch implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    /**
     * 标题
     */
    @Field(type = FieldType.Text, analyzer = "ik_max_word")
    private String title;

    /**
     * 文章作者的ID
     */
    @Field(type = FieldType.Integer)
    private Integer authorId;

    /**
     * 作者名称
     */
    @Field(type = FieldType.Keyword)
    private String authorName;


    /**
     * 文章布局
     * 0 无图文章
     * 1 单图文章
     * 2 多图文章
     */
    @Field(type = FieldType.Integer)
    private Integer layout;


    /**
     * 文章图片
     * 多张逗号分隔
     */
    @Field(type = FieldType.Keyword, index = false)
    private String images;

    /**
     * 静态地址
     */
    @Field(type = FieldType.Keyword, index = false)
    private String staticUrl;


    /**
     * 发布时间
     */
    @Field(type = FieldType.Date)
    private Date publishTime;

}
