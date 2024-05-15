package smr.shop.brand.service.exception;

import org.springframework.http.HttpStatus;
import smr.shop.libs.common.exception.GlobalException;

/**
 * Author: Ali Gadashov
 * Version: v1.0
 * Date: 5/14/2024
 * Time: 4:34 PM
 */

public class CategoryException extends GlobalException {

    public CategoryException(String message, HttpStatus status) {
        super(message, status);
    }

    public CategoryException(String message, HttpStatus status, Throwable cause) {
        super(message, status, cause);
    }
}
