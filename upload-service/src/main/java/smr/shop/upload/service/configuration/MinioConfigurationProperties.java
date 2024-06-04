package smr.shop.upload.service.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "minio")
@Getter
@Setter
public class MinioConfigurationProperties {
    private String endpoint;
    private String accessKey;
    private String secretKey;
}
