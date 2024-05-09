package smr.shop.coupon.service.exception;

import org.springframework.http.HttpStatus;
import smr.shop.libs.common.exception.GlobalException;

/**
 * Author: Ali Gadashov
 * Version: v1.0
 * Date: 5/9/2024
 * Time: 3:32 PM
 */

public class CouponException extends GlobalException {

    public CouponException(String message, HttpStatus status) {
        super(message, status);
    }

    public CouponException(String message, HttpStatus status, Throwable cause) {
        super(message, status, cause);
    }

}
