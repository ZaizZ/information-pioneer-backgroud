package pioneer.article;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class ArticleApp {
    public static void main(String[] args) {
        SpringApplication.run(ArticleApp.class, args);
    }
}
