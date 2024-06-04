package smr.shop.upload.service.configuration;

import io.minio.MinioClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfiguration {

    private final MinioConfigurationProperties minioConfigurationProperties;

    public BeanConfiguration(MinioConfigurationProperties minioConfigurationProperties) {
        this.minioConfigurationProperties = minioConfigurationProperties;
    }

    @Bean
    public MinioClient minioClient() {

        return MinioClient.builder()
                .endpoint(minioConfigurationProperties.getEndpoint())
                .credentials(minioConfigurationProperties.getAccessKey(), minioConfigurationProperties.getSecretKey())
                .build();
    }
}
