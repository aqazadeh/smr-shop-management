package smr.shop.flash.service.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import smr.shop.flash.service.exception.FlashServiceException;
import smr.shop.libs.common.dto.response.ErrorResponse;
import smr.shop.libs.common.exception.handler.GlobalExceptionHandler;

@RestControllerAdvice
@Slf4j
public class FlashServiceExceptionHandler extends GlobalExceptionHandler {
    @ExceptionHandler({FlashServiceException.class})
    public ResponseEntity<ErrorResponse> handle(FlashServiceException exception) {
        log.error(exception.getMessage(), exception);
        ErrorResponse response = ErrorResponse.builder().code(exception.getStatus()).message(exception.getMessage()).build();
        return ResponseEntity.status(exception.getStatus()).body(response);
    }
}
