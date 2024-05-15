package smr.shop.brand.service.grpc.client;

import smr.shop.libs.grpc.upload.UploadGrpcResponse;

public interface BrandGrpcClientService {
    UploadGrpcResponse getImage(String id);
}
