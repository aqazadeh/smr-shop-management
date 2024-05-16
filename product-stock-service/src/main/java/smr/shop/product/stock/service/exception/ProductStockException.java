package smr.shop.product.stock.service.exception;

import org.springframework.http.HttpStatus;
import smr.shop.libs.common.exception.GlobalException;

public class ProductStockException extends GlobalException {

    public ProductStockException(String message, HttpStatus status) {
        super(message, status);
    }

    public ProductStockException(String message, HttpStatus status, Throwable cause) {
        super(message, status, cause);
    }
}