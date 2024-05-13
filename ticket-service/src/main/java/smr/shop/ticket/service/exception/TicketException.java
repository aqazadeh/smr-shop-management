package smr.shop.ticket.service.exception;

import org.springframework.http.HttpStatus;
import smr.shop.libs.common.exception.GlobalException;

public class TicketException extends GlobalException {
    public TicketException(String message, HttpStatus status) {
        super(message, status);
    }

    public TicketException(String message, HttpStatus status, Throwable cause) {
        super(message, status, cause);
    }
}
