package smr.shop.coupon.service.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import smr.shop.coupon.service.exception.CouponServiceException;
import smr.shop.libs.common.dto.response.ErrorResponse;
import smr.shop.libs.common.exception.handler.GlobalExceptionHandler;

@RestControllerAdvice
@Slf4j
public class CouponServiceExceptionHandler extends GlobalExceptionHandler {

    @ExceptionHandler(CouponServiceException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> globalException(CouponServiceException exception) {
        log.error("an error occurred", exception);
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(exception.getStatus())
                .message(exception.getMessage())
                .build();
        return ResponseEntity.status(exception.getStatus()).body(errorResponse);
    }
}
