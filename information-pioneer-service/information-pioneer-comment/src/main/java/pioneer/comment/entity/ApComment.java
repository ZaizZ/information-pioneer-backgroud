package pioneer.comment.entity;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.util.Date;

/**
 * APP评论信息
 */
@Data
@Document("ap_comment")
public class ApComment {

    /**
     * id
     */
    private String id;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 用户昵称
     */
    private String userName;

    /**
     * 用户头像
     */
    private String image;

    /**
     * 文章id
     */
    private Long articleId;

    /**
     * 评论内容
     */
    private String content;

    /**
     * 点赞数
     */
    private Integer likes = 0;

    /**
     * 回复数
     */
    private Integer repay = 0;

    /**
     * 创建时间
     */
    private Date createdTime;

}
