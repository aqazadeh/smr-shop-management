package smr.shop.product.review.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ProductReviewServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProductReviewServiceApplication.class, args);
    }
}