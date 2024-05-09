package smr.shop.brand.service.exception;

import org.springframework.http.HttpStatus;
import smr.shop.libs.common.exception.GlobalException;

public class BrandException extends GlobalException {

    public BrandException(String message, HttpStatus status) {
        super(message, status);
    }

    public BrandException(String message, HttpStatus status, Throwable cause) {
        super(message, status, cause);
    }
}
