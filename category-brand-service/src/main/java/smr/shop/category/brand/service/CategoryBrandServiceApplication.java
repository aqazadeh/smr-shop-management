package smr.shop.category.brand.service;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"smr.shop.category.brand.service", "smr.shop.libs"})
@EntityScan(basePackages = {"smr.shop.category.brand.service.model", "smr.shop.libs.outbox.model"})
@EnableJpaRepositories({"smr.shop.category.brand.service.repository", "smr.shop.libs.outbox.repository"})
@EnableDiscoveryClient
public class CategoryBrandServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(CategoryBrandServiceApplication.class, args);
    }
}