package pioneer.article.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"pioneer.common.exception","pioneer.common.knife4j","pioneer.common.minio"})
public class initConfig {
}
