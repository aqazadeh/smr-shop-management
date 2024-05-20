package smr.shop.wishlist.service.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import smr.shop.libs.common.dto.response.ErrorResponse;
import smr.shop.libs.common.exception.handler.GlobalExceptionHandler;
import smr.shop.wishlist.service.exception.WishlistServiceException;

@RestControllerAdvice
@Slf4j
public class WishlistServiceExceptionHandler extends GlobalExceptionHandler {

    @ExceptionHandler(WishlistServiceException.class)
    public ResponseEntity<ErrorResponse> handle(WishlistServiceException exception) {
        log.error(exception.getMessage(), exception);
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(exception.getStatus())
                .message(exception.getMessage())
                .build();
        return ResponseEntity.ok(errorResponse);

    }

}
