package smr.shop.courier.service.exception;

import org.springframework.http.HttpStatus;
import smr.shop.libs.common.exception.GlobalException;

public class CourierServiceException extends GlobalException {
    public CourierServiceException(String message, HttpStatus status) {
        super(message, status);
    }

    public CourierServiceException(String message, HttpStatus status, Throwable cause) {
        super(message, status, cause);
    }
}
