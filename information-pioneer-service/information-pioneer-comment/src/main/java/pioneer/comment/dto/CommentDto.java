package pioneer.comment.dto;

import lombok.Data;

import java.util.Date;

@Data
public class CommentDto {

    /**
     * 文章id
     */
    private Long articleId;
    /**
     * 显示条数
     */
    private Integer size;
    /**
     * 最小时间
     */
    private Date minDate;
    /**
     * 是否首页
     */
    private Integer index;
}
