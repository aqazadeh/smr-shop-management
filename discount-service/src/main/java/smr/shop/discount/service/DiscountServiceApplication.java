package smr.shop.discount.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"smr.shop.discount.service.*", "smr.shop.libs.*"})
@EnableDiscoveryClient
public class DiscountServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DiscountServiceApplication.class, args);
    }
}