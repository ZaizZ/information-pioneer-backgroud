package pioneer.gateway.filter;

import io.jsonwebtoken.Claims;
import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import pioneer.gateway.utils.AppJwtUtil;
import reactor.core.publisher.Mono;

@Component
public class AuthFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        //需要放行的URL
        if (request.getURI().getPath().contains("/login/in")) {
            return chain.filter(exchange);
        }
        //获得携带的token
        String token = request.getHeaders().getFirst("token");
        //判断是否携带token
        if(StringUtils.isBlank(token)){
            //为空则拦截，返回状态码
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }

//        如果携带token判断token是否有效
        Claims claims = AppJwtUtil.getClaimsBody(token);
        if(claims!=null){
            //        如果有效从token中解析出userId和name放到请求头中继续进入到后面的微服务中
            int type = AppJwtUtil.verifyToken(claims);
//            -1：有效，0：有效 1 2 过期
            if(type==-1||type==0){
                //进入到相应的微服务中，需要携带当前操作的用户，不能用Threadlocal的原因是因为网关和微服务可能部署在不同的服务器,不属于一个进程
                //在登录的时候放了这两个信息
                Integer userId = claims.get("userId", Integer.class);
                String name = claims.get("name", String.class);
                request.mutate().header("userId",userId.toString());
                request.mutate().header("name",name);
                return chain.filter(exchange);  //放行
            }
        }

        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete();
    }

    //执行顺序 值越小，优先级越高
    @Override
    public int getOrder() {
        return 0;
    }
}
