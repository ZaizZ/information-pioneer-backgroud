package pioneer.comment.service.impl;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import pioneer.comment.dto.CommentRepayDto;
import pioneer.comment.dto.CommentRepayLikeDto;
import pioneer.comment.dto.CommentRepaySaveDto;
import pioneer.comment.entity.ApCommentRepay;
import pioneer.comment.entity.ApCommentRepayLike;
import pioneer.comment.service.ICommentRepayService;
import pioneer.comment.vo.ApCommentRepayVo;
import pioneer.common.dto.ResponseResult;
import pioneer.common.dto.User;
import pioneer.common.enums.AppHttpCodeEnum;
import pioneer.common.util.UserThreadLocalUtil;

@Service
public class CommentRepayServiceImpl implements ICommentRepayService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Override
    public ResponseResult saveCommentRepay(CommentRepaySaveDto dto) {
        if (dto == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        User user = UserThreadLocalUtil.get();
        if (user == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }

        ApCommentRepay apCommentRepay = new ApCommentRepay();
        apCommentRepay.setUserId(user.getUserId());
        apCommentRepay.setUserName(user.getName());
        apCommentRepay.setCommentId(dto.getCommentId());
        apCommentRepay.setContent(dto.getContent());
        apCommentRepay.setLikes(0);
        apCommentRepay.setCreatedTime(new Date());

        mongoTemplate.save(apCommentRepay);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult loadCommentRepay(CommentRepayDto dto) {
        if (dto == null || dto.getCommentId()==null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        User user = UserThreadLocalUtil.get();

        Query query = new Query(Criteria.where("commentId").is(dto.getCommentId()).and("createdTime").lt(dto.getMinDate()));
        query.with(Sort.by(Sort.Direction.DESC,"createdTime"));
        query.limit(5);
        List<ApCommentRepay> apCommentRepays = mongoTemplate.find(query, ApCommentRepay.class);

        apCommentRepays = apCommentRepays.stream().map(commentRepay ->{
            ApCommentRepayVo apCommentRepayVo = new ApCommentRepayVo();
            BeanUtils.copyProperties(commentRepay, apCommentRepayVo);
            if (user!=null){
                Boolean aBoolean = stringRedisTemplate.opsForHash().hasKey("ap_comment_repay_like" + user.getUserId(), commentRepay.getId());
                apCommentRepayVo.setOperation(aBoolean ? 0: 1);
            }
            return apCommentRepayVo;
        } ).collect(Collectors.toList());

        return  ResponseResult.okResult(apCommentRepays);
    }

    @Override
    public ResponseResult commentRepayLike(CommentRepayLikeDto dto) {
        if (dto == null) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        User user = UserThreadLocalUtil.get();
        if (user == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }

        ApCommentRepayLike apCommentRepayLike = new ApCommentRepayLike();
        apCommentRepayLike.setUserId(user.getUserId());
        apCommentRepayLike.setCommentRepayId(dto.getCommentRepayId());
        apCommentRepayLike.setOperation(dto.getOperation());
        apCommentRepayLike.setCreatedTime(new Date());

        mongoTemplate.save(apCommentRepayLike);

        Query query = new Query(Criteria.where("_id").is(dto.getCommentRepayId()));
        Update update = new Update();
        if (dto.getOperation() == 0){
            update.inc("likes",1);
            stringRedisTemplate.opsForHash().put("ap_comment_repay_like" + user.getUserId(), dto.getCommentRepayId(), "");
        }else{
            update.inc("likes",-1);
            stringRedisTemplate.opsForHash().delete("ap_comment_repay_like" + user.getUserId(), dto.getCommentRepayId());
        }

        mongoTemplate.updateFirst(query,update,ApCommentRepay.class);

        return ResponseResult.okResult();
    }

}
