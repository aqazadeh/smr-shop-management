package smr.shop.shop.service.grpc.client;

import smr.shop.libs.grpc.upload.UploadGrpcResponse;

public interface UploadGrpcServiceClient {
    UploadGrpcResponse getImageById(String string);
}
