package smr.shop.upload.service.exception;

import org.springframework.http.HttpStatus;
import smr.shop.libs.common.exception.GlobalException;

public class UploadServiceException extends GlobalException {
    public UploadServiceException(String message, HttpStatus status) {
        super(message, status);
    }

    public UploadServiceException(String message, HttpStatus status, Throwable cause) {
        super(message, status, cause);
    }
}
