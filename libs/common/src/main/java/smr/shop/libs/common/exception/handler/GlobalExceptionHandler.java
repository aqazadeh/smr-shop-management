package smr.shop.libs.common.exception.handler;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import smr.shop.libs.common.exception.GlobalException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public String exception(Exception e) {
        return e.getMessage();
    }

    @ExceptionHandler(GlobalException.class)
    public String globalException(GlobalException e) {
        return e.getMessage();
    }
}
