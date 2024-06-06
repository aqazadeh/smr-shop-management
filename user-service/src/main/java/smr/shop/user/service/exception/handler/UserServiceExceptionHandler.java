package smr.shop.user.service.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import smr.shop.user.service.exception.UserServiceException;
import smr.shop.libs.common.dto.response.ErrorResponse;
import smr.shop.libs.common.exception.handler.GlobalExceptionHandler;

@RestControllerAdvice
@Slf4j
public class UserServiceExceptionHandler extends GlobalExceptionHandler {

    @ExceptionHandler(UserServiceException.class)
    public ResponseEntity<ErrorResponse> handleUserServiceException(UserServiceException exception) {
        log.error(exception.getMessage(), exception);
        ErrorResponse response = ErrorResponse.builder().message(exception.getMessage()).code(exception.getStatus()).build();
        return ResponseEntity.status(exception.getStatus()).body(response);
    }
}
