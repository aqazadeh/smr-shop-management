package smr.shop.category.brand.service.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import smr.shop.category.brand.service.exception.CategoryBrandServiceException;
import smr.shop.libs.common.dto.response.ErrorResponse;
import smr.shop.libs.common.exception.handler.GlobalExceptionHandler;

@RestControllerAdvice
@Slf4j
public class CategoryBrandServiceExceptionHandler extends GlobalExceptionHandler {

    @ExceptionHandler(CategoryBrandServiceException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> globalException(CategoryBrandServiceException exception) {
        log.error("an error occurred", exception);
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(exception.getStatus())
                .message(exception.getMessage())
                .build();
        return ResponseEntity.status(exception.getStatus()).body(errorResponse);
    }

}
