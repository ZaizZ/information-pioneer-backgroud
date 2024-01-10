package pioneer.common.minio;

import io.minio.MinioClient;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableConfigurationProperties(MinIOProperties.class)
public class MinIOConfig {
    @Bean
    public MinioClient minioClient(MinIOProperties minIOProperties){
        MinioClient minioClient = new MinioClient(minIOProperties.getEndpoint(), minIOProperties.getAccessKey(), minIOProperties.getSecretKey());
        return minioClient;
    }
}
