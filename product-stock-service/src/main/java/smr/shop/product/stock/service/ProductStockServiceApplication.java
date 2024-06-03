package smr.shop.product.stock.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"smr.shop.product.stock.service", "smr.shop.libs"})
@EntityScan(basePackages = {"smr.shop.product.stock.service", "smr.shop.libs"})
@EnableJpaRepositories(basePackages = {"smr.shop.product.stock.service", "smr.shop.libs"})
@EnableDiscoveryClient
public class ProductStockServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProductStockServiceApplication.class, args);
    }
}