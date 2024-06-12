package smr.shop.upload.service.mapper;

import org.springframework.stereotype.Component;
import smr.shop.libs.grpc.upload.UploadGrpcResponse;
import smr.shop.upload.service.dto.response.UploadResponse;
import smr.shop.upload.service.model.UploadEntity;

@Component
public class UploadServiceMapper {
    public UploadGrpcResponse uploadEntityToUploadGrpcResponse(UploadEntity uploadEntity) {
        return UploadGrpcResponse.newBuilder()
                .setId(uploadEntity.getId().toString())
                .setUrl("/api/1.0/uploads/images/" + uploadEntity.getUrl())
                .build();

    }

    public UploadResponse uploadEntityToUploadResponse(UploadEntity uploadEntity) {
        return UploadResponse.builder()
                .id(uploadEntity.getId())
                .url("/api/1.0/uploads/images/" + uploadEntity.getUrl())
                .build();
    }
}
