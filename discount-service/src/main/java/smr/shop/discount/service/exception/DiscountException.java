package smr.shop.discount.service.exception;

import org.springframework.http.HttpStatus;
import smr.shop.libs.common.exception.GlobalException;

public class DiscountException extends GlobalException {
    public DiscountException(String message, HttpStatus status) {
        super(message, status);
    }

    public DiscountException(String message, HttpStatus status, Throwable cause) {
        super(message, status, cause);
    }
}
