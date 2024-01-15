package pioneer.behavior.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan({"pioneer.common.exception","pioneer.common.knife4j"})
public class initConfig {
}
