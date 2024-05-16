package smr.shop.cart.service.grpc;

import smr.shop.libs.grpc.coupon.CouponGrpcResponse;
import smr.shop.libs.grpc.product.ProductGrpcResponse;
import smr.shop.libs.grpc.product.stock.ProductStockGrpcResponse;

import java.util.UUID;

public interface CartGrpcClientService {
    CouponGrpcResponse getCouponDetailWithCode(String code);

    ProductGrpcResponse getProduct(Long productId);

    ProductStockGrpcResponse getAttribute(UUID attributeId);
}
