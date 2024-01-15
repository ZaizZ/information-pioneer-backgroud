package pioneer.behavior.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import pioneer.behavior.dto.BehaviorDto;
import pioneer.behavior.service.IBehaviorService;
import pioneer.common.dto.ResponseResult;
import pioneer.common.dto.User;
import pioneer.common.util.UserThreadLocalUtil;

import java.util.HashMap;
import java.util.Map;

@Service
public class BehaviorServiceImpl implements IBehaviorService{
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public ResponseResult loadBehavior(BehaviorDto dto) {

        User user = UserThreadLocalUtil.get();
        if (user==null) {
            return null; //不要报错，没有用户也可以加载行为，只是为空而已
        }


        Map map = new HashMap();
        //关注作者跟文章没有关系   点赞、不喜欢、收藏跟作者没有关系  所以在相应的位置都是null
        map.put("isfollow",redisTemplate.hasKey("saveFollow_"+user.getUserId()+"_"+dto.getAuthorId()));
        map.put("islike",redisTemplate.hasKey("saveLikes_"+user.getUserId()+"_null_"+dto.getArticleId()));
        map.put("isunlike",redisTemplate.hasKey("saveUnlikes_"+user.getUserId()+"_null_"+dto.getArticleId()));
        map.put("iscollection",redisTemplate.hasKey("saveCollection_"+user.getUserId()+"_null_"+dto.getArticleId()));


        return ResponseResult.okResult(map);
    }
}
