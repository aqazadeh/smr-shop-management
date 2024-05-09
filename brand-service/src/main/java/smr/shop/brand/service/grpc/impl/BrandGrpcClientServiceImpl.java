package smr.shop.brand.service.grpc.impl;

import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import smr.shop.libs.grpc.upload.UploadGrpcRequest;
import smr.shop.libs.grpc.upload.UploadGrpcResponse;
import smr.shop.libs.grpc.upload.UploadServiceGrpc;

import java.util.UUID;


@Service
public class BrandGrpcClientServiceImpl {
    @GrpcClient("upload-service")
    private UploadServiceGrpc.UploadServiceBlockingStub blockingStub;

    public UploadGrpcResponse getImage() {
        UploadGrpcRequest request = UploadGrpcRequest.newBuilder()
                .setId(UUID.randomUUID().toString())
                .build();
        return blockingStub.getUpload(request);
    }
}
