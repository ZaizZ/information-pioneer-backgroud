package pioneer.comment.dto;

import lombok.Data;
import java.util.Date;

@Data
public class CommentRepayDto {

    /**
     * 评论id
     */
    private String commentId;
    /**
     * 显示条数
     */
    private Integer size;
    /**
     * 最小时间
     */
    private Date minDate;
}
