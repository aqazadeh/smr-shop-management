package smr.shop.category.brand.service.grpc.server;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import smr.shop.category.brand.service.service.BrandService;
import smr.shop.libs.grpc.brand.BrandGrpcResponse;
import smr.shop.libs.grpc.brand.BrandServiceGrpc;
import smr.shop.libs.grpc.object.BrandGrpcId;

@GrpcService
public class BrandGrpcServer extends BrandServiceGrpc.BrandServiceImplBase {

    private final BrandService brandService;

    public BrandGrpcServer(BrandService brandService) {
        this.brandService = brandService;
    }

    @Override
    public void getBrandByBrandId(BrandGrpcId request, StreamObserver<BrandGrpcResponse> responseObserver) {
        BrandGrpcResponse brandGrpcResponse = brandService.getBrandInformation(request);
        responseObserver.onNext(brandGrpcResponse);
        responseObserver.onCompleted();
    }
}