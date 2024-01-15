package pioneer.comment.service;

import pioneer.comment.dto.CommentRepayDto;
import pioneer.comment.dto.CommentRepayLikeDto;
import pioneer.comment.dto.CommentRepaySaveDto;
import pioneer.common.dto.ResponseResult;

public interface ICommentRepayService {
    ResponseResult saveCommentRepay(CommentRepaySaveDto dto);

    ResponseResult loadCommentRepay(CommentRepayDto dto);

    ResponseResult commentRepayLike(CommentRepayLikeDto dto);
}
