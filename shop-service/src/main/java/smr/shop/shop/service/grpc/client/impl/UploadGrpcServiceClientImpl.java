package smr.shop.shop.service.grpc.client.impl;

import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Component;
import smr.shop.libs.grpc.upload.UploadGrpcRequest;
import smr.shop.libs.grpc.upload.UploadGrpcResponse;
import smr.shop.libs.grpc.upload.UploadServiceGrpc;
import smr.shop.shop.service.grpc.client.UploadGrpcServiceClient;

@Component
public class UploadGrpcServiceClientImpl implements UploadGrpcServiceClient {

    @GrpcClient("upload-service")
    private UploadServiceGrpc.UploadServiceBlockingStub uploadServiceBlockingStub;

    @Override
    public UploadGrpcResponse getImageById(String string) {
        UploadGrpcRequest uploadGrpcRequest = UploadGrpcRequest.newBuilder().setId(string).build();
        return uploadServiceBlockingStub.getUpload(uploadGrpcRequest);
//        return UploadGrpcResponse.newBuilder().setUrl("http://image.com").setId("e3209d3a-b016-4ca2-a346-c21aa3243cc9").build();
    }
}
