package smr.shop.libs.grpc.client;

import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;
import smr.shop.libs.grpc.coupon.CouponGrpcResponse;
import smr.shop.libs.grpc.coupon.CouponServiceGrpc;
import smr.shop.libs.grpc.object.CouponGrpcCode;

@Component
public class CouponGrpcClient {

    @GrpcClient("coupon-service")
    private CouponServiceGrpc.CouponServiceBlockingStub couponServiceBlockingStub;

    public CouponGrpcResponse getCouponByCode(String code) {
        CouponGrpcCode couponCodeGrpcRequest = CouponGrpcCode.newBuilder().setCode(code).build();
        return couponServiceBlockingStub.getCouponByCode(couponCodeGrpcRequest);
    }
}
