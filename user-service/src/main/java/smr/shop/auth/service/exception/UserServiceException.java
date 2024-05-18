package smr.shop.auth.service.exception;

import org.springframework.http.HttpStatus;
import smr.shop.libs.common.exception.GlobalException;

public class UserServiceException extends GlobalException {
    public UserServiceException(String message, HttpStatus status) {
        super(message, status);
    }

    public UserServiceException(String message, HttpStatus status, Throwable cause) {
        super(message, status, cause);
    }
}