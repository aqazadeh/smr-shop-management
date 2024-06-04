package smr.shop.coupon.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"smr.shop.coupon.service", "smr.shop.libs"})
@EntityScan(basePackages = {"smr.shop.coupon.service.model", "smr.shop.libs.outbox.model"})
@EnableJpaRepositories({"smr.shop.coupon.service.repository", "smr.shop.libs.outbox.repository"})
@EnableDiscoveryClient
public class CouponServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(CouponServerApplication.class, args);
    }
}