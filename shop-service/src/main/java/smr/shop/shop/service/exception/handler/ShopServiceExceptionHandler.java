package smr.shop.shop.service.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import smr.shop.libs.common.dto.response.ErrorResponse;
import smr.shop.libs.common.exception.handler.GlobalExceptionHandler;
import smr.shop.shop.service.exception.ShopServiceException;

@RestControllerAdvice
@Slf4j
public class ShopServiceExceptionHandler extends GlobalExceptionHandler {

    @ExceptionHandler(ShopServiceException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handle(ShopServiceException exception) {
        log.error(exception.getMessage(), exception);
        ErrorResponse response = ErrorResponse.builder().code(exception.getStatus()).message(exception.getMessage()).build();
        return ResponseEntity.status(exception.getStatus()).body(response);
    }
}
