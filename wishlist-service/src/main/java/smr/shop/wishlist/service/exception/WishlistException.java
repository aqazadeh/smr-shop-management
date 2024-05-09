package smr.shop.wishlist.service.exception;

import org.springframework.http.HttpStatus;
import smr.shop.libs.common.exception.GlobalException;

public class WishlistException extends GlobalException {
    public WishlistException(String message, HttpStatus status) {
        super(message, status);
    }

    public WishlistException(String message, HttpStatus status, Throwable cause) {
        super(message, status, cause);
    }
}
