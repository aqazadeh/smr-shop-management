package smr.shop.brand.service.grpc.server;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import smr.shop.brand.service.service.BrandService;
import smr.shop.libs.grpc.brand.BrandGrpcRequest;
import smr.shop.libs.grpc.brand.BrandGrpcResponse;
import smr.shop.libs.grpc.brand.BrandServiceGrpc;

@GrpcService
public class BrandGrpcServerService extends BrandServiceGrpc.BrandServiceImplBase {

    private final BrandService brandService;

    public BrandGrpcServerService(BrandService brandService) {
        this.brandService = brandService;
    }

    @Override
    public void getBrand(BrandGrpcRequest request, StreamObserver<BrandGrpcResponse> responseObserver) {
        BrandGrpcResponse brandGrpcResponse = brandService.getBrandInformation(request);
        responseObserver.onNext(brandGrpcResponse);
        responseObserver.onCompleted();
    }
}