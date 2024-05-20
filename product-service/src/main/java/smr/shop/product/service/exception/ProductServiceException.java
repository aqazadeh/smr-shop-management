package smr.shop.product.service.exception;

import org.springframework.http.HttpStatus;
import smr.shop.libs.common.exception.GlobalException;

/**
 * Author: Ali Gadashov
 * Version: v1.0
 * Date: 5/11/2024
 * Time: 2:03 AM
 */

public class ProductServiceException extends GlobalException {

    public ProductServiceException(String message, HttpStatus status) {
        super(message, status);
    }

    public ProductServiceException(String message, HttpStatus status, Throwable cause) {
        super(message, status, cause);
    }
}
