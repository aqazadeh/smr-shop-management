package smr.shop.ticket.service.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import smr.shop.libs.common.dto.response.ErrorResponse;
import smr.shop.libs.common.exception.handler.GlobalExceptionHandler;
import smr.shop.ticket.service.exception.TicketServiceException;

@RestControllerAdvice
@Slf4j
public class TicketServiceExceptionHandler extends GlobalExceptionHandler {

    @ExceptionHandler(TicketServiceException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handle(TicketServiceException exception) {
        log.error(exception.getMessage(), exception);
        ErrorResponse response = ErrorResponse.builder()
                .code(exception.getStatus())
                .message(exception.getMessage())
                .build();
        return ResponseEntity.ok(response);
    }

}
