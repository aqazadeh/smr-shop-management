package smr.shop.delivery.service.exception;

import org.springframework.http.HttpStatus;
import smr.shop.libs.common.exception.GlobalException;

public class DeliveryConversationException extends GlobalException {
    public DeliveryConversationException(String message, HttpStatus status) {
        super(message, status);
    }
}
