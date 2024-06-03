package smr.shop.upload.service.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import smr.shop.upload.service.exception.UploadServiceException;
import smr.shop.upload.service.service.UploadService;

import java.util.UUID;

@Component
public class UploadServiceImpl implements UploadService {
    @Override
    public void error() {
        throw new UploadServiceException("image not found with id: " + UUID.randomUUID(), HttpStatus.NOT_FOUND);
    }
}
