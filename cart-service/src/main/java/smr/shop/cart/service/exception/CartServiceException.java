package smr.shop.cart.service.exception;

import org.springframework.http.HttpStatus;
import smr.shop.libs.common.exception.GlobalException;

/**
 * Author: Ali Gadashov
 * Version: v1.0
 * Date: 5/10/2024
 * Time: 1:35 PM
 */

public class CartServiceException extends GlobalException {

    public CartServiceException(String message, HttpStatus status) {
        super(message, status);
    }

    public CartServiceException(String message, HttpStatus status, Throwable cause) {
        super(message, status, cause);
    }

}
