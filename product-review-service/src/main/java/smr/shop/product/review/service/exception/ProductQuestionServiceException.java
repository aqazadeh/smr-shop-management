package smr.shop.product.review.service.exception;

import org.springframework.http.HttpStatus;
import smr.shop.libs.common.exception.GlobalException;

public class ProductQuestionServiceException extends GlobalException {
    public ProductQuestionServiceException(String message, HttpStatus status) {
        super(message, status);
    }

    public ProductQuestionServiceException(String message, HttpStatus status, Throwable cause) {
        super(message, status, cause);
    }
}
