package smr.shop.product.review.service.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import smr.shop.libs.common.dto.response.ErrorResponse;
import smr.shop.libs.common.exception.handler.GlobalExceptionHandler;
import smr.shop.product.review.service.exception.ProductReviewServiceException;

@RestControllerAdvice
@Slf4j
public class ProductReviewServiceExceptionHandler extends GlobalExceptionHandler {

    @ExceptionHandler(ProductReviewServiceException.class)
    public ResponseEntity<ErrorResponse> handle(ProductReviewServiceException exception) {
        log.error(exception.getMessage(), exception.getCause());
        ErrorResponse response = ErrorResponse.builder()
                .code(exception.getStatus())
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(exception.getStatus()).body(response);
    }
}
