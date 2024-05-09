package smr.shop.brand.service.grpc.impl;

import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;
import smr.shop.brand.service.grpc.BrandGrpcClientService;
import smr.shop.libs.grpc.upload.UploadGrpcRequest;
import smr.shop.libs.grpc.upload.UploadGrpcResponse;
import smr.shop.libs.grpc.upload.UploadServiceGrpc;


@Service
public class BrandGrpcClientServiceImpl implements BrandGrpcClientService {
    @GrpcClient("upload-service")
    private UploadServiceGrpc.UploadServiceBlockingStub blockingStub;

    public UploadGrpcResponse getImage(String id) {
        UploadGrpcRequest request = UploadGrpcRequest.newBuilder()
                .setId(id)
                .build();
        return blockingStub.getUpload(request);
    }
}
