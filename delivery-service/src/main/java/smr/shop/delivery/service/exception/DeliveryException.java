package smr.shop.delivery.service.exception;

import org.springframework.http.HttpStatus;
import smr.shop.libs.common.exception.GlobalException;

public class DeliveryException extends GlobalException {
    public DeliveryException(String message, HttpStatus status) {
        super(message, status);
    }

    public DeliveryException(String message, HttpStatus status, Throwable cause) {
        super(message, status, cause);
    }
}
