package smr.shop.wishlist.service.exception;

import org.springframework.http.HttpStatus;
import smr.shop.libs.common.exception.GlobalException;

public class WishlistServiceException extends GlobalException {
    public WishlistServiceException(String message, HttpStatus status) {
        super(message, status);
    }

    public WishlistServiceException(String message, HttpStatus status, Throwable cause) {
        super(message, status, cause);
    }
}
