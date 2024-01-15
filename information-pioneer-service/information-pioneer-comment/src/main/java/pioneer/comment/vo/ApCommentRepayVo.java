package pioneer.comment.vo;


import lombok.Data;
import lombok.EqualsAndHashCode;
import pioneer.comment.entity.ApCommentRepay;

@Data
@EqualsAndHashCode(callSuper = true)
public class ApCommentRepayVo extends ApCommentRepay {

    /**
     * 0：点赞
     * 1：取消点赞
     */
    private Integer operation=1;
}
