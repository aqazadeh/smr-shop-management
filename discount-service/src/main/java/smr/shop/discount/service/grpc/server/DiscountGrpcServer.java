package smr.shop.discount.service.grpc.server;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import smr.discount.libs.grpc.product.discount.DiscountGrpcResponse;
import smr.discount.libs.grpc.product.discount.DiscountServiceGrpc;
import smr.shop.discount.service.service.DiscountService;
import smr.shop.libs.grpc.object.DiscountGrpcId;

@GrpcService
public class DiscountGrpcServer extends DiscountServiceGrpc.DiscountServiceImplBase {

    private final DiscountService discountService;

    public DiscountGrpcServer(DiscountService discountService) {
        this.discountService = discountService;
    }

    @Override
    public void getDiscountById(DiscountGrpcId request, StreamObserver<DiscountGrpcResponse> responseObserver) {
        DiscountGrpcResponse discountGrpcResponse = discountService.getDiscountById(request);
        responseObserver.onNext(discountGrpcResponse);
        responseObserver.onCompleted();
    }
}
