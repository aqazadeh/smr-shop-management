package smr.shop.upload.service.grpc.server;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import smr.shop.libs.grpc.object.UploadGrpcId;
import smr.shop.libs.grpc.upload.UploadGrpcResponse;
import smr.shop.libs.grpc.upload.UploadServiceGrpc;
import smr.shop.upload.service.service.UploadService;

@GrpcService
public class GrpcServerService extends UploadServiceGrpc.UploadServiceImplBase {

    private final UploadService uploadService;

    public GrpcServerService(UploadService uploadService) {
        this.uploadService = uploadService;
    }

    @Override
    public void getUploadById(UploadGrpcId request, StreamObserver<UploadGrpcResponse> responseObserver) {
        UploadGrpcResponse response = uploadService.getImageById(request);
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
