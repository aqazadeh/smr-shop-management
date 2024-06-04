package smr.shop.upload.service.service;

import org.springframework.web.multipart.MultipartFile;
import smr.shop.libs.common.dto.message.UploadMessageModel;
import smr.shop.libs.grpc.object.UploadGrpcId;
import smr.shop.libs.grpc.upload.UploadGrpcResponse;
import smr.shop.upload.service.dto.response.UploadResponse;
import smr.shop.upload.service.model.UploadEntity;

import java.util.UUID;

public interface UploadService {

    UploadResponse upload(MultipartFile files);

    UploadGrpcResponse getImageById(UploadGrpcId request);

    byte[] getImage(String url);

    void delete(UploadMessageModel message);

    UploadEntity findImageById(String filename);

    UploadEntity findById(UUID uploadId);
}
