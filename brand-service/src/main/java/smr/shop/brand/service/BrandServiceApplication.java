package smr.shop.brand.service;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableDiscoveryClient
public class BrandServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BrandServiceApplication.class, args);
    }
    // TODO created updated time fix
}