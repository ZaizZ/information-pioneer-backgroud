package pioneer.comment.dto;

import lombok.Data;

@Data
public class CommentSaveDto {

    /**
     * 文章id
     */
    private Long articleId;

    /**
     * 频道id
     */
    private Integer channelId;

    /**
     * 评论内容
     */
    private String content;
}
