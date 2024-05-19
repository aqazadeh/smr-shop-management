package smr.shop.coupon.service.grpc.server;

import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Service;
import smr.shop.coupon.service.service.CouponService;
import smr.shop.libs.grpc.coupon.CouponGrpcResponse;
import smr.shop.libs.grpc.coupon.CouponServiceGrpc;
import smr.shop.libs.grpc.coupon.CouponUsageGrpcResponse;
import smr.shop.libs.grpc.object.CouponGrpcCode;
import smr.shop.libs.grpc.object.CouponUsageGrpc;

@Service
public class CouponGrpcServer extends CouponServiceGrpc.CouponServiceImplBase {

    private final CouponService couponService;

    public CouponGrpcServer(CouponService couponService) {
        this.couponService = couponService;
    }

    @Override
    public void getCouponByCode(CouponGrpcCode request, StreamObserver<CouponGrpcResponse> responseObserver) {
        CouponGrpcResponse response = couponService.getCouponDetail(request);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void getCouponUsage(CouponUsageGrpc request, StreamObserver<CouponUsageGrpcResponse> responseObserver) {
        CouponUsageGrpcResponse response = couponService.getCouponUsage(request);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
