package smr.shop.brand.service;


import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import smr.shop.brand.service.grpc.impl.BrandGrpcClientServiceImpl;
import smr.shop.libs.grpc.upload.UploadGrpcResponse;

@SpringBootApplication
@EnableDiscoveryClient
public class BrandServiceApplication implements CommandLineRunner {
    private final BrandGrpcClientServiceImpl brandGrpcClientServiceImpl;

    public BrandServiceApplication(BrandGrpcClientServiceImpl brandGrpcClientServiceImpl) {
        this.brandGrpcClientServiceImpl = brandGrpcClientServiceImpl;
    }

    public static void main(String[] args) {
        SpringApplication.run(BrandServiceApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        UploadGrpcResponse brand = brandGrpcClientServiceImpl.getImage("ggfh");
        System.out.println(brand.getUrl());
    }
}