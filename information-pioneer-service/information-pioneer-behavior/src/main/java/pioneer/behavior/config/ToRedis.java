package pioneer.behavior.config;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) //注解的运行时期
@Target({ElementType.METHOD}) //注解要加在哪个上面
public @interface ToRedis {
}
