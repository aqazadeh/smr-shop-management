package smr.shop.upload.service.service.grpc;

import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import smr.shop.libs.grpc.upload.UploadGrpcRequest;
import smr.shop.libs.grpc.upload.UploadGrpcResponse;
import smr.shop.libs.grpc.upload.UploadServiceGrpc;

import java.util.UUID;

@GrpcService
public class GrpcServerService extends UploadServiceGrpc.UploadServiceImplBase {

    @Override
    public void getUpload(UploadGrpcRequest request, StreamObserver<UploadGrpcResponse> responseObserver) {
        UploadGrpcResponse response = UploadGrpcResponse.newBuilder()
                .setUrl("sdhajhkfb").build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
