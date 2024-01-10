package pioneer.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
@SpringBootApplication
public class AdminGateway {
    public static void main(String[] args) {
        SpringApplication.run(AdminGateway.class, args);
    }
}

