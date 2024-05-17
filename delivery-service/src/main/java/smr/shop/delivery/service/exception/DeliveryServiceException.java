package smr.shop.delivery.service.exception;

import org.springframework.http.HttpStatus;
import smr.shop.libs.common.exception.GlobalException;

public class DeliveryServiceException extends GlobalException {
    public DeliveryServiceException(String message, HttpStatus status) {
        super(message, status);
    }

    public DeliveryServiceException(String message, HttpStatus status, Throwable cause) {
        super(message, status, cause);
    }
}
