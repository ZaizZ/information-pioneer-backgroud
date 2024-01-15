package pioneer.comment.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import pioneer.comment.dto.CommentDto;
import pioneer.comment.dto.CommentLikeDto;
import pioneer.comment.dto.CommentSaveDto;
import pioneer.comment.service.ICommentService;
import pioneer.common.dto.ResponseResult;

import java.util.List;

@RestController
@RequestMapping("/api/v1/comment")
public class CommentController {
    @Autowired
    private ICommentService commentService;

    @PostMapping("/save")
    @ApiOperation("发表评论")
    public ResponseResult saveComment(@RequestBody CommentSaveDto dto){
        return commentService.saveComment(dto);
    }

    @PostMapping("/load")
    @ApiOperation("根据文章id显示评论")
    public ResponseResult<List<CommentSaveDto>> loadComment(@RequestBody CommentDto dto) {
        return commentService.loadComment(dto);
    }

    @PostMapping("/like")
    @ApiOperation("对评论点赞或取消点赞")
    public ResponseResult commentLike(@RequestBody CommentLikeDto dto){
        return commentService.commentLike(dto);
    }
}
