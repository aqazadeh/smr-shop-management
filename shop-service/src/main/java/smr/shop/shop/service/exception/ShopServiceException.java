package smr.shop.shop.service.exception;

import org.springframework.http.HttpStatus;
import smr.shop.libs.common.exception.GlobalException;

public class ShopServiceException extends GlobalException {
    public ShopServiceException(String message, HttpStatus status) {
        super(message, status);
    }

    public ShopServiceException(String message, HttpStatus status, Throwable cause) {
        super(message, status, cause);
    }
}
