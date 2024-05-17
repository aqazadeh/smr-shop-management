package smr.shop.product.review.service.exception;

import org.springframework.http.HttpStatus;
import smr.shop.libs.common.exception.GlobalException;

public class ProductReviewServiceException extends GlobalException {
    public ProductReviewServiceException(String message, HttpStatus status) {
        super(message, status);
    }

    public ProductReviewServiceException(String message, HttpStatus status, Throwable cause) {
        super(message, status, cause);
    }
}
