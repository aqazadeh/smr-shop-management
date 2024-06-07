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

    public ShopGrpcResponse getShopByUserId(String userId) {
        UserGrpcId request = UserGrpcId.newBuilder().setId(userId).build();
       return shopServiceBlockingStub.getShopByUserId(request);
    }

    public ShopGrpcResponse getShopByShopId(Long shopId) {
        ShopGrpcId request = ShopGrpcId.newBuilder().setId(shopId).build();
        return shopServiceBlockingStub.getShopByShopId(request);
    }
}
