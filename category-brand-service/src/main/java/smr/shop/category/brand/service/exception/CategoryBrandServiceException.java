package smr.shop.category.brand.service.exception;

import org.springframework.http.HttpStatus;
import smr.shop.libs.common.exception.GlobalException;

public class CategoryBrandServiceException extends GlobalException {

    public CategoryBrandServiceException(String message, HttpStatus status) {
        super(message, status);
    }

    public CategoryBrandServiceException(String message, HttpStatus status, Throwable cause) {
        super(message, status, cause);
    }
}
