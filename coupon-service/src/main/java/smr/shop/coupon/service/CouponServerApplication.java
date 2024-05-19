package smr.shop.coupon.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"smr.shop.coupon.service.*", "smr.shop.libs.*"})
@EnableDiscoveryClient
public class CouponServerApplication {
    public static void main(String[] args) {
        SpringApplication.run(CouponServerApplication.class, args);
    }
}