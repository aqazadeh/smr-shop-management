package smr.shop.coupon.service.grpc.client.impl;

import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import smr.shop.coupon.service.grpc.client.CouponClientGrpcService;
import smr.shop.libs.grpc.product.shop.FindShopByUserIdGrpcRequest;
import smr.shop.libs.grpc.product.shop.ShopGrpcResponse;
import smr.shop.libs.grpc.product.shop.ShopServiceGrpc;

import java.util.UUID;

@Service
@Slf4j
public class CouponClientGrpcServiceImpl implements CouponClientGrpcService {

    @GrpcClient("shop-service")
    private ShopServiceGrpc.ShopServiceBlockingStub shopServiceBlockingStub;

    @Override
    public ShopGrpcResponse shopInformationByUser(UUID userId) {
        FindShopByUserIdGrpcRequest grpcRequest = FindShopByUserIdGrpcRequest.newBuilder().setUserId(userId.toString()).build();
        return shopServiceBlockingStub.getShopInformationByUserId(grpcRequest);
    }
}
