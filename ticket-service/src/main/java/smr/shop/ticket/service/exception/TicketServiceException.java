package smr.shop.ticket.service.exception;

import org.springframework.http.HttpStatus;
import smr.shop.libs.common.exception.GlobalException;

public class TicketServiceException extends GlobalException {
    public TicketServiceException(String message, HttpStatus status) {
        super(message, status);
    }

    public TicketServiceException(String message, HttpStatus status, Throwable cause) {
        super(message, status, cause);
    }
}
