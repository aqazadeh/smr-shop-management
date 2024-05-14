package smr.shop.coupon.service.grpc.client;

import smr.shop.libs.grpc.product.shop.ShopGrpcResponse;

public interface CouponClientGrpcService {
    ShopGrpcResponse getShopByUserId(Long userId);
}
