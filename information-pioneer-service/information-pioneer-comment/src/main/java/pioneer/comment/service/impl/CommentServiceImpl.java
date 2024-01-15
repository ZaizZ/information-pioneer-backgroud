package pioneer.comment.service.impl;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import pioneer.comment.client.ArticleFeign;
import pioneer.comment.client.UserFeign;
import pioneer.comment.dto.CommentDto;
import pioneer.comment.dto.CommentLikeDto;
import pioneer.comment.dto.CommentSaveDto;
import pioneer.comment.entity.ApArticle;
import pioneer.comment.entity.ApComment;
import pioneer.comment.entity.ApCommentLike;
import pioneer.comment.entity.ApUser;
import pioneer.comment.service.ICommentService;
import pioneer.comment.vo.ApCommentVo;
import pioneer.common.dto.ResponseResult;
import pioneer.common.dto.User;
import pioneer.common.enums.AppHttpCodeEnum;
import pioneer.common.util.UserThreadLocalUtil;

@Service
public class CommentServiceImpl implements ICommentService {

    @Autowired
    private UserFeign userFeign;
    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ArticleFeign articleFeign;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Value("${topic.comment}")
    private String topic;

    @Override
    public ResponseResult saveComment(CommentSaveDto dto) {
        if (dto == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        User user = UserThreadLocalUtil.get();
        if (user == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }
        if (dto.getContent().length() > 140 || StringUtils.isBlank(dto.getContent())) {
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }

        ApComment apComment = new ApComment();
        apComment.setUserId(user.getUserId());
        apComment.setUserName(user.getName());
        //远程调用查询用户的信息
        ResponseResult<ApUser> apUser = userFeign.getById(user.getUserId());

        apComment.setImage(apUser.getData().getImage());
        apComment.setArticleId(dto.getArticleId());
        apComment.setContent(dto.getContent());
        apComment.setLikes(0);
        apComment.setRepay(0);
        apComment.setCreatedTime(new Date());

        mongoTemplate.save(apComment);

        HashMap map = new HashMap();
        map.put("articleId", dto.getArticleId());
        map.put("comment",1);
        kafkaTemplate.send(topic, JSON.toJSONString(map));

        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult<List<CommentSaveDto>> loadComment(CommentDto dto) {

        User user = UserThreadLocalUtil.get();
        if (user == null){
            return null; //不要报错，没有用户也可以加载评论，只是为空而已
        }

        if (dto == null || dto.getArticleId()==null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        //如果文章被下架或删除了就不加载评论了
        ResponseResult<ApArticle> apArticleResponseResult = articleFeign.loadById(dto.getArticleId());
        ApArticle apArticle = apArticleResponseResult.getData();
        if (apArticle == null || apArticle.getIsDown() == true || apArticle.getIsDelete() == true) {
            return ResponseResult.errorResult(AppHttpCodeEnum.DATA_NOT_EXIST);
        }

        Query query = new Query(Criteria.where("articleId").is(dto.getArticleId()).and("createdTime").lt(dto.getMinDate()));
        query.with(Sort.by(Sort.Direction.DESC, "createdTime")); //排序
        query.limit(10);
        List<ApComment> apComments = mongoTemplate.find(query, ApComment.class);

        //加上当前登录人是否点赞这条评论的操作
        apComments = apComments.stream().map(comment->{
            ApCommentVo apCommentVo = new ApCommentVo();
            BeanUtils.copyProperties(comment, apCommentVo);
            if(user!=null){
                //查看Redis中用户是否对这条评论点赞了 放Redis是因为MongoDB中数据量太大，每次都查效率不高
                Boolean aBoolean = stringRedisTemplate.opsForHash().hasKey("comment_likes_" + user.getUserId(), comment.getId());
                //0:点赞  1：没有点赞
                apCommentVo.setOperation(aBoolean ? 0 : 1);
            }
            return apCommentVo;
        }).collect(Collectors.toList());

        return ResponseResult.okResult(apComments);
    }

    @Override
    public ResponseResult commentLike(CommentLikeDto dto) {
        if (dto == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.PARAM_INVALID);
        }
        User user = UserThreadLocalUtil.get();
        if (user == null){
            return ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
        }
        ApCommentLike apCommentLike = new ApCommentLike();
        apCommentLike.setUserId(user.getUserId());
        apCommentLike.setCommentId(dto.getCommentId());
        apCommentLike.setCreateTime(new Date());
        apCommentLike.setOperation(dto.getOperation());

        mongoTemplate.save(apCommentLike);
        //修改评论收到的点赞数
        Query query = new Query(Criteria.where("_id").is(dto.getCommentId()));
        Update update = new Update();
        if(dto.getOperation()==0){
            //点赞 点赞数加1
            update.inc("likes", 1);
            //往Redis中存一个
            stringRedisTemplate.opsForHash().put("comment_likes_"+ user.getUserId(), dto.getCommentId(),"");
        }else {
            //取消点赞 点赞数减1
            update.inc("likes", -1);
            stringRedisTemplate.opsForHash().delete("comment_likes_"+user.getUserId(), dto.getCommentId());
        }
        mongoTemplate.updateFirst(query,update, ApComment.class);
        return ResponseResult.okResult();
    }
}
