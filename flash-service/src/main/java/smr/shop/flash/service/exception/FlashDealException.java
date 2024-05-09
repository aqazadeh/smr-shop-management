package smr.shop.flash.service.exception;

import org.springframework.http.HttpStatus;
import smr.shop.libs.common.exception.GlobalException;

public class FlashDealException extends GlobalException {
    public FlashDealException(String message, HttpStatus status) {
        super(message, status);
    }

    public FlashDealException(String message, HttpStatus status, Throwable cause) {
        super(message, status, cause);
    }
}
