package pioneer.comment.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pioneer.comment.entity.ApComment;

@Data
@EqualsAndHashCode(callSuper = true)
public class ApCommentVo extends ApComment {

    /**
     * 0：点赞
     * 1：取消点赞
     * 给默认值是因为用户可能点的是“不登录，先看看”，所以默认是没有点赞的
     */
    private Integer operation=1;
}
