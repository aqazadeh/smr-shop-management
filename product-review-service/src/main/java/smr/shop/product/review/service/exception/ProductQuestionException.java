package smr.shop.product.review.service.exception;

import org.springframework.http.HttpStatus;
import smr.shop.libs.common.exception.GlobalException;

public class ProductQuestionException extends GlobalException {
    public ProductQuestionException(String message, HttpStatus status) {
        super(message, status);
    }

    public ProductQuestionException(String message, HttpStatus status, Throwable cause) {
        super(message, status, cause);
    }
}
