package smr.shop.shop.service.grpc.server;

import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Component;
import smr.shop.libs.grpc.object.ShopGrpcId;
import smr.shop.libs.grpc.object.UserGrpcId;
import smr.shop.libs.grpc.product.shop.ShopGrpcResponse;
import smr.shop.libs.grpc.product.shop.ShopServiceGrpc;
import smr.shop.shop.service.service.ShopService;

@Component
public class ShopGrpcServerService extends ShopServiceGrpc.ShopServiceImplBase {
    private final ShopService shopService;

    public ShopGrpcServerService(ShopService shopService) {
        this.shopService = shopService;
    }

    @Override
    public void getShopByShopId(ShopGrpcId request, StreamObserver<ShopGrpcResponse> responseObserver) {
        ShopGrpcResponse shopGrpcResponse = shopService.getShopInformationByShopId(request);
        responseObserver.onNext(shopGrpcResponse);
        responseObserver.onCompleted();
    }

    @Override
    public void getShopByUserId(UserGrpcId request, StreamObserver<ShopGrpcResponse> responseObserver) {
        ShopGrpcResponse shopGrpcResponse = shopService.getShopInformationByUserId(request);
        responseObserver.onNext(shopGrpcResponse);
        responseObserver.onCompleted();
    }
}
