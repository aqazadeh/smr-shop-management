package smr.shop.flash.service.exception;

import org.springframework.http.HttpStatus;
import smr.shop.libs.common.exception.GlobalException;

/**
 * Author: Ali Gadashov
 * Version: v1.0
 * Date: 5/9/2024
 * Time: 7:46 PM
 */

public class FlashDealProductException extends GlobalException {
    public FlashDealProductException(String message, HttpStatus status) {
        super(message, status);
    }

    public FlashDealProductException(String message, HttpStatus status, Throwable cause) {
        super(message, status, cause);
    }
}
