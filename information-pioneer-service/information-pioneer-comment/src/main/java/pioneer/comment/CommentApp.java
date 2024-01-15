package pioneer.comment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;


@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class}) //父类的依赖里面有需要配置数据源的依赖，用这个排除掉
@EnableFeignClients
public class CommentApp {
    public static void main(String[] args) {
        SpringApplication.run(CommentApp.class, args);
    }
}
