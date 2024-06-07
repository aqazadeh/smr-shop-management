package smr.shop.upload.service.service.impl;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.ObjectWriteResponse;
import io.minio.PutObjectArgs;
import io.minio.errors.MinioException;
import jakarta.validation.constraints.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import smr.shop.libs.common.constant.ServiceConstants;
import smr.shop.libs.common.dto.message.UploadMessageModel;
import smr.shop.libs.grpc.object.UploadGrpcId;
import smr.shop.libs.grpc.upload.UploadGrpcResponse;
import smr.shop.upload.service.dto.response.UploadResponse;
import smr.shop.upload.service.exception.UploadServiceException;
import smr.shop.upload.service.mapper.UploadServiceMapper;
import smr.shop.upload.service.model.UploadEntity;
import smr.shop.upload.service.model.valueobject.UploadStatus;
import smr.shop.upload.service.repository.UploadRepository;
import smr.shop.upload.service.service.UploadService;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Component
public class UploadServiceImpl implements UploadService {


    private final UploadRepository uploadRepository;
    private final UploadServiceMapper uploadServiceMapper;
    private final MinioClient minioClient;
    private static List<String> imageTypes = List.of("jpg", "jpeg", "png");

    public UploadServiceImpl(UploadRepository uploadRepository,
                             UploadServiceMapper uploadServiceMapper,
                             MinioClient minioClient) {
        this.uploadRepository = uploadRepository;
        this.uploadServiceMapper = uploadServiceMapper;
        this.minioClient = minioClient;
    }


    //   ------------------ Create
    @Override
    public UploadResponse upload(MultipartFile file) {

        String filename = uploadFileToCloud(file);

        UploadEntity uploadEntity = UploadEntity.builder()
                .id(UUID.randomUUID())
                .status(UploadStatus.USING)
                .url(filename)
                .createdAt(ZonedDateTime.now(ServiceConstants.ZONE_ID))
                .updatedAt(ZonedDateTime.now(ServiceConstants.ZONE_ID))
                .build();
        uploadRepository.save(uploadEntity);
        return uploadServiceMapper.uploadEntityToUploadResponse(uploadEntity);
    }


    //   ------------------ Get
    @Override
    public UploadGrpcResponse getImageById(UploadGrpcId request) {
        UUID imageId = UUID.fromString(request.getId());
        UploadEntity uploadEntity = findById(imageId);
        return uploadServiceMapper.uploadEntityToUploadGrpcResponse(uploadEntity);
    }

    @Override
    public byte[] getImage(String url) {
        UploadEntity uploadEntity = findImageById(url);
        GetObjectArgs getObjectArgs = GetObjectArgs
                .builder()
                .bucket("image-bucket")
                .object(uploadEntity.getUrl())
                .build();
        try {
            InputStream stream = minioClient.getObject(getObjectArgs);
            return stream.readAllBytes();
        } catch (MinioException | InvalidKeyException | NoSuchAlgorithmException | IOException e) {
            throw new UploadServiceException("file get fail, try again", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //   ------------------ Delete
    @Override
    @Transactional
    public void delete(UploadMessageModel message) {
        String imageUrl = message.getImageUrl();
        String[] split = imageUrl.split("/");
        String object = split[split.length - 1];
        UploadEntity uploadEntity = findImageById(object);
        uploadEntity.setStatus(UploadStatus.DELETED);
        uploadRepository.save(uploadEntity);
    }

    //   ------------------ Extra

    @Override
    public UploadEntity findImageById(String filename) {
        return uploadRepository.findByUrl(filename)
                .orElseThrow((() -> new UploadServiceException("image not found with url: " + filename, HttpStatus.BAD_REQUEST)));
    }

    @Override
    public UploadEntity findById(UUID uploadId) {
        return uploadRepository.findById(uploadId)
                .orElseThrow(() -> new UploadServiceException("image not found with id: " + uploadId, HttpStatus.BAD_REQUEST));
    }


    private String uploadFileToCloud(MultipartFile file) {
        isAllowedFiles(file);

        try {
            String filename = UUID.randomUUID().toString();
            InputStream inputStream = file.getInputStream();

            ObjectWriteResponse objectWriteResponse = minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket("image-bucket")
                            .object(filename)
                            .contentType(MediaType.IMAGE_PNG_VALUE)
                            .stream(inputStream, inputStream.available(), -1)
                            .build());
            System.out.println(objectWriteResponse.object());
            System.out.println(objectWriteResponse.etag());
            System.out.println(objectWriteResponse.bucket());
            System.out.println(objectWriteResponse.region());
            System.out.println(objectWriteResponse.versionId());

            return filename;
        } catch (MinioException | IOException | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new UploadServiceException("file upload fail, try again", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    private void isAllowedFiles(@NotNull MultipartFile file) {


        String fileName = file.getOriginalFilename();
        int length = fileName.split("\\.").length;
        if (length < 2)
            throw new UploadServiceException("unknown file format", HttpStatus.BAD_REQUEST);

        String extension = fileName.split("\\.")[1];
        if (!imageTypes.contains(extension))
            throw new UploadServiceException("file not supported", HttpStatus.BAD_REQUEST);

    }
}
