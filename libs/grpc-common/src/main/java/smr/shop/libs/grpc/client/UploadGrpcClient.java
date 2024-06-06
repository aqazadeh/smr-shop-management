package smr.shop.libs.grpc.client;

import lombok.extern.slf4j.Slf4j;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;
import smr.shop.libs.grpc.object.UploadGrpcId;
import smr.shop.libs.grpc.upload.UploadGrpcResponse;
import smr.shop.libs.grpc.upload.UploadServiceGrpc;

@Component
@Slf4j
public class UploadGrpcClient {

    @GrpcClient("upload-service")
    private UploadServiceGrpc.UploadServiceBlockingStub uploadServiceBlockingStub;

    public UploadGrpcResponse getUploadById(String uploadId) {
        UploadGrpcId request = UploadGrpcId.newBuilder().setId(uploadId).build();
        return uploadServiceBlockingStub.getUploadById(request);
    }

}
