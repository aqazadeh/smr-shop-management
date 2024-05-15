package smr.shop.coupon.service.grpc.client;

import smr.shop.libs.grpc.product.shop.ShopGrpcResponse;

import java.util.UUID;

public interface CouponClientGrpcService {
    ShopGrpcResponse shopInformationByUser(UUID userId);
}
