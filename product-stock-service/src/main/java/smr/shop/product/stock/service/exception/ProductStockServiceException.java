package smr.shop.product.stock.service.exception;

import org.springframework.http.HttpStatus;
import smr.shop.libs.common.exception.GlobalException;

public class ProductStockServiceException extends GlobalException {

    public ProductStockServiceException(String message, HttpStatus status) {
        super(message, status);
    }

    public ProductStockServiceException(String message, HttpStatus status, Throwable cause) {
        super(message, status, cause);
    }
}