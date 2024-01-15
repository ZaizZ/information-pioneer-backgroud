package pioneer.comment.service;

import pioneer.comment.dto.CommentDto;
import pioneer.comment.dto.CommentLikeDto;
import pioneer.comment.dto.CommentSaveDto;
import pioneer.common.dto.ResponseResult;

import java.util.List;

public interface ICommentService {
    ResponseResult saveComment(CommentSaveDto dto);

    ResponseResult<List<CommentSaveDto>> loadComment(CommentDto dto);

    ResponseResult commentLike(CommentLikeDto dto);
}
