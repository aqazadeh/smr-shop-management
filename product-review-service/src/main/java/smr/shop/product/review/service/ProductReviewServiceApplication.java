package smr.shop.product.review.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"smr.shop.product.review.service", "smr.shop.libs"})
@EnableDiscoveryClient
public class ProductReviewServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProductReviewServiceApplication.class, args);
    }
}