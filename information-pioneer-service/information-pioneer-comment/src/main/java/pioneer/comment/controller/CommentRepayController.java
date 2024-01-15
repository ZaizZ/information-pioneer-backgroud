package pioneer.comment.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pioneer.comment.dto.CommentRepayDto;
import pioneer.comment.dto.CommentRepayLikeDto;
import pioneer.comment.dto.CommentRepaySaveDto;
import pioneer.comment.service.ICommentRepayService;
import pioneer.common.dto.ResponseResult;

@RestController
@RequestMapping("/api/v1/comment_repay")
public class CommentRepayController {

    @Autowired
    private ICommentRepayService commentRepayService;

    @PostMapping("/save")
    @ApiOperation("评论回复")
    public ResponseResult saveCommentRepay(@RequestBody CommentRepaySaveDto dto) {
        return commentRepayService.saveCommentRepay(dto);
    }

    @PostMapping("/load")
    @ApiOperation("根据评论id加载回复评论")
    public ResponseResult loadCommentRepay(@RequestBody CommentRepayDto dto) {
        return commentRepayService.loadCommentRepay(dto);
    }

    @PostMapping("/like")
    @ApiOperation("点赞回复评论")
    public ResponseResult commentRepayLike(@RequestBody CommentRepayLikeDto dto) {
        return commentRepayService.commentRepayLike(dto);
    }



}
