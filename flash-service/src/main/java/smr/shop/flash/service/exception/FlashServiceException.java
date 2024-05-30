package smr.shop.flash.service.exception;

import org.springframework.http.HttpStatus;
import smr.shop.libs.common.exception.GlobalException;

public class FlashServiceException extends GlobalException {
    public FlashServiceException(String message, HttpStatus status) {
        super(message, status);
    }

    public FlashServiceException(String message, HttpStatus status, Throwable cause) {
        super(message, status, cause);
    }
}
