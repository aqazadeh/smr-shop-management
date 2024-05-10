package smr.shop.cart.service.grpc;

import smr.shop.libs.grpc.coupon.CouponGrpcResponse;
import smr.shop.libs.grpc.product.ProductGrpcResponse;
import smr.shop.libs.grpc.product.attribute.ProductAttributeGrpcResponse;

import java.util.UUID;

public interface CartGrpcClientService {
    CouponGrpcResponse getCouponDetailWithCode(String code);

    CouponGrpcResponse getCouponDetailWithId(UUID couponId);

    ProductGrpcResponse getProduct(Long productId);

    ProductAttributeGrpcResponse getAttribute(UUID attributeId);
}
