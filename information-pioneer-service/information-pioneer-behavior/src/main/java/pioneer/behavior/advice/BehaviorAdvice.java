package pioneer.behavior.advice;

import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import pioneer.behavior.config.ToRedis;
import pioneer.behavior.dto.BehaviorDto;

@Component
@Aspect //声明这是一个切面类
public class BehaviorAdvice {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

                    //任意返回值   ..表示impl下面的所有包 *.*(..)任意类下的任意方法的任意参数
    @Around("execution(* pioneer.behavior.service.impl..*.*(..))") //环绕通知
    public Object saveBehaviorToRedis(ProceedingJoinPoint pjp) {
        Object obj = null;
        try {
            //执行原方法
            obj = pjp.proceed();

            //获得需要增强方法的所有东西
            MethodSignature methodSignature = (MethodSignature) pjp.getSignature();
            //只有方法上加了@ToRedis注解的才增强
            boolean annotationPresent = methodSignature.getMethod().isAnnotationPresent(ToRedis.class);
            if (annotationPresent) {
                //获得方法名
                String methodName = methodSignature.getMethod().getName();
                //获得方法的参数
//        Object[] args = pjp.getArgs(); //得到的是一个数组，但是需要的增强的方法只有一个参数
                BehaviorDto dto = (BehaviorDto) pjp.getArgs()[0];
                Integer userId = dto.getUserId();
                Long articleId = dto.getArticleId();
                Integer authorId = dto.getAuthorId();
                if (dto.getOperation() == 0) {
                    //将用户的行为保存进入Redis
                    if(StringUtils.equals("saveFollow",methodName)){
                        //关注用户不需要文章的id
                        stringRedisTemplate.opsForValue().set(methodName + "_" + userId + "_" + authorId, "");
                    }else {
                        stringRedisTemplate.opsForValue().set(methodName + "_" + userId + "_" + authorId + "_" + articleId, "");
                    }
                } else {
                    if(StringUtils.equals("saveFollow",methodName)){
                        stringRedisTemplate.delete(methodName + "_" + userId + "_" + authorId);
                    }else {
                        stringRedisTemplate.delete(methodName + "_" + userId + "_" + authorId + "_" + articleId);
                    }
                }
            }
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        return obj;
    }

}
