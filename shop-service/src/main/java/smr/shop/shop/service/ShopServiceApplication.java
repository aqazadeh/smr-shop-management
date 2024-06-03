package smr.shop.shop.service;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"smr.shop.shop.service.*", "smr.shop.libs.*"})
@EntityScan(basePackages = {"smr.shop.shop.service.*", "smr.shop.libs.*"})
@EnableJpaRepositories(basePackages = {"smr.shop.shop.service.*", "smr.shop.libs.*"})
@EnableDiscoveryClient
public class ShopServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShopServiceApplication.class, args);
    }
}