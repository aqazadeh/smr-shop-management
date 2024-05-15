package smr.shop.coupon.service.grpc.client.impl;

import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import smr.shop.coupon.service.grpc.client.CouponClientGrpcService;
import smr.shop.libs.grpc.product.shop.ShopGrpcResponse;

@Service
@Slf4j
public class CouponClientGrpcServiceImpl implements CouponClientGrpcService {
    @Override
    @GrpcClient("shop-service")
    public ShopGrpcResponse getShopByUserId(Long userId) {
        return null;
    }
}
