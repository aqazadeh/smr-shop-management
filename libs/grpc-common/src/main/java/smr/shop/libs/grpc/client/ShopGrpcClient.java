package smr.shop.libs.grpc.client;

import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;
import smr.shop.libs.grpc.object.ShopGrpcId;
import smr.shop.libs.grpc.object.UserGrpcId;
import smr.shop.libs.grpc.product.shop.ShopGrpcResponse;
import smr.shop.libs.grpc.product.shop.ShopServiceGrpc;

@Component
public class ShopGrpcClient {

    @GrpcClient("shop-service")
    private ShopServiceGrpc.ShopServiceBlockingStub shopServiceBlockingStub;

    public ShopGrpcResponse getShopByUserId(UserGrpcId request) {
//       return shopServiceBlockingStub.getShopByUserId(request);
        return ShopGrpcResponse.newBuilder()
                .setId(1)
                .setUserId("4d88de81-c88b-418e-8a9b-7fc2d4cc14ee")
                .setSlug("shop-1")
                .setName("Shop 1")
                .setLogo("test-logo")
                .build();
    }

    public ShopGrpcResponse getShopByShopId(ShopGrpcId request) {
        return shopServiceBlockingStub.getShopByShopId(request);
    }
}
