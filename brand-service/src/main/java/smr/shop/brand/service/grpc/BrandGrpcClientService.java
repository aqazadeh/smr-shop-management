package smr.shop.brand.service.grpc;

import smr.shop.libs.grpc.upload.UploadGrpcResponse;

public interface BrandGrpcClientService {
    UploadGrpcResponse getImage(String id);
}
