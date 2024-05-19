package smr.shop.libs.grpc.client;

import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;
import smr.shop.libs.grpc.brand.BrandGrpcResponse;
import smr.shop.libs.grpc.brand.BrandServiceGrpc;
import smr.shop.libs.grpc.object.BrandGrpcId;

@Component
public class BrandGrpcClient {

    @GrpcClient("brand-service")
    public BrandServiceGrpc.BrandServiceBlockingStub brandServiceBlockingStub;

    public BrandGrpcResponse getBrandByBrandId(Long brandId) {
        BrandGrpcId request = BrandGrpcId.newBuilder().setId(brandId).build();
//        return brandServiceBlockingStub.getBrandByBrandId(request);

        return BrandGrpcResponse.newBuilder()
                .setId(1)
                .setName("brand 1")
                .setSlug("brand-1")
                .setDescription("brand description")
                .setImageUrl("test-image")
                .build();
    }
}
