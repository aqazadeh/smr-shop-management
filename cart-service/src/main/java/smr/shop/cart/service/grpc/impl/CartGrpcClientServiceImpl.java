package smr.shop.cart.service.grpc.impl;

import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import smr.shop.cart.service.grpc.CartGrpcClientService;
import smr.shop.libs.grpc.coupon.CouponCodeGrpcRequest;
import smr.shop.libs.grpc.coupon.CouponGrpcResponse;
import smr.shop.libs.grpc.coupon.CouponIdGrpcRequest;
import smr.shop.libs.grpc.coupon.CouponServiceGrpc;
import smr.shop.libs.grpc.product.ProductGrpcRequest;
import smr.shop.libs.grpc.product.ProductGrpcResponse;
import smr.shop.libs.grpc.product.ProductServiceGrpc;
import smr.shop.libs.grpc.product.stock.ProductStockGrpcResponse;
import smr.shop.libs.grpc.product.stock.ProductStockServiceGrpc;
import smr.shop.libs.grpc.product.stock.ProductStockWithIdGrpcRequest;

import java.util.UUID;

@Service
@Slf4j
public class CartGrpcClientServiceImpl implements CartGrpcClientService {

    @GrpcClient("coupon-service")
    private CouponServiceGrpc.CouponServiceBlockingStub couponServiceBlockingStub;

    @GrpcClient("product-service")
    private ProductServiceGrpc.ProductServiceBlockingStub productServiceBlockingStub;

    @GrpcClient("product-attribute-service")
    private ProductStockServiceGrpc.ProductStockServiceBlockingStub productStockServiceBlockingStub;



    @Override
    public CouponGrpcResponse getCouponDetailWithCode(String code) {
        CouponCodeGrpcRequest couponCodeGrpcRequest = CouponCodeGrpcRequest.newBuilder().setCode(code).build();
        CouponGrpcResponse couponDetailWithCode = couponServiceBlockingStub.getCouponDetailWithCode(couponCodeGrpcRequest);
        return couponDetailWithCode;
    }

    @Override
    public CouponGrpcResponse getCouponDetailWithId(UUID couponId) {
        CouponIdGrpcRequest couponIdGrpcRequest = CouponIdGrpcRequest.newBuilder().setId(couponId.toString()).build();
        CouponGrpcResponse couponDetailWithId = couponServiceBlockingStub.getCouponDetailWithId(couponIdGrpcRequest);
        return couponDetailWithId;
    }

    @Override
    public ProductGrpcResponse getProduct(Long productId) {
        ProductGrpcRequest productGrpcRequest = ProductGrpcRequest.newBuilder().setProductId(productId).build();
        ProductGrpcResponse productInfo = productServiceBlockingStub.getProductInfo(productGrpcRequest);
        return productInfo;
    }

    @Override
    public ProductStockGrpcResponse getAttribute(UUID attributeId) {
        ProductStockWithIdGrpcRequest productStockWithIdGrpcRequest = ProductStockWithIdGrpcRequest.newBuilder().setStockId(attributeId.toString()).build();
        productStockServiceBlockingStub.getProductStockId(productStockWithIdGrpcRequest);
        return null;
    }
}
