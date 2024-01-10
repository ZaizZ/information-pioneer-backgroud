package pioneer.media.interceptor;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import pioneer.common.dto.User;
import pioneer.common.util.UserThreadLocalUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserInterceptor implements HandlerInterceptor {
    @Override //进入到Controller之前
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //获取请求头中的用户信息
        String userId = request.getHeader("userId");
        String name = request.getHeader("name");
        if (StringUtils.isNotBlank(userId)){
            User user = new User(Integer.parseInt(userId), name);
            //放入ThreadLocal中
            UserThreadLocalUtil.set(user);
        }
        return true; //全部放行
    }

    @Override //从Controller出来之后
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        UserThreadLocalUtil.remove();
    }


}
