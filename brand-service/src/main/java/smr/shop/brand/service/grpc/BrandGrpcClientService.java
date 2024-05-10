package smr.shop.brand.service.grpc;

import smr.shop.libs.grpc.upload.UploadGrpcListResponse;
import smr.shop.libs.grpc.upload.UploadGrpcResponse;

import java.util.List;

public interface BrandGrpcClientService {
    UploadGrpcResponse getImage(String id);
}
