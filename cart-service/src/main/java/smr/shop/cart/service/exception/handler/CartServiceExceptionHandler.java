package smr.shop.cart.service.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import smr.shop.cart.service.exception.CartServiceException;
import smr.shop.libs.common.dto.response.ErrorResponse;
import smr.shop.libs.common.exception.handler.GlobalExceptionHandler;

@RestControllerAdvice
@Slf4j
public class CartServiceExceptionHandler extends GlobalExceptionHandler {

    @ExceptionHandler(CartServiceException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> exceptionHandler(CartServiceException exception) {
        log.error("CartServiceException: {}", exception.getMessage());
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(exception.getStatus())
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(exception.getStatus()).body(errorResponse);
    }
}
