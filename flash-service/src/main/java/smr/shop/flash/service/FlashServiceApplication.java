package smr.shop.flash.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"smr.shop.flash.service.*", "smr.shop.libs.*"})
@EnableDiscoveryClient
public class FlashServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(FlashServiceApplication.class, args);
    }
}