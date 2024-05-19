package smr.shop.libs.grpc.client;

import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;
import smr.shop.libs.grpc.object.UploadGrpcId;
import smr.shop.libs.grpc.upload.UploadGrpcResponse;
import smr.shop.libs.grpc.upload.UploadServiceGrpc;

@Component
public class UploadGrpcClient {

    @GrpcClient("upload-service")
    private UploadServiceGrpc.UploadServiceBlockingStub uploadServiceBlockingStub;

    public UploadGrpcResponse getUploadById(String uploadId) {
        UploadGrpcId request = UploadGrpcId.newBuilder().setId(uploadId).build();
//        return uploadServiceBlockingStub.getUploadById(request);
        return UploadGrpcResponse.newBuilder()
                .setId("tfa85f64-5717-4562-b3fc-2c963f66afa6")
                .setUrl("test-url")
                .build();

    }

}
