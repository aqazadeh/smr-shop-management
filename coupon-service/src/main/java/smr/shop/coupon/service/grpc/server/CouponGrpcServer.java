package smr.shop.coupon.service.grpc.server;

import io.grpc.stub.StreamObserver;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import smr.shop.coupon.service.service.CouponService;
import smr.shop.libs.grpc.coupon.CouponGrpcRequest;
import smr.shop.libs.grpc.coupon.CouponGrpcResponse;
import smr.shop.libs.grpc.coupon.CouponServiceGrpc;

@Service
public class CouponGrpcServer extends CouponServiceGrpc.CouponServiceImplBase {

    private final CouponService couponService;

    public CouponGrpcServer(CouponService couponService) {
        this.couponService = couponService;
    }

    @Override
    public void getCouponDetail(CouponGrpcRequest request, StreamObserver<CouponGrpcResponse> responseObserver) {
        CouponGrpcResponse couponDetail = couponService.getCouponDetail(request);
        responseObserver.onNext(couponDetail);
        responseObserver.onCompleted();
    }
}
