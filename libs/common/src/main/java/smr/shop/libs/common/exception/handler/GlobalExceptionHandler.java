package smr.shop.libs.common.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import smr.shop.libs.common.dto.response.ErrorResponse;
import smr.shop.libs.common.exception.GlobalException;

import java.util.List;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handle(Exception exception) {
        log.error(exception.getMessage(), exception);
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
    }

    @ExceptionHandler(GlobalException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponse> handle(GlobalException exception) {
        log.error(exception.getMessage(), exception);
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(exception.getStatus())
                .message(exception.getMessage())
                .build();

        return ResponseEntity.status(exception.getStatus()).body(errorResponse);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {

        log.error(exception.getMessage(), exception);

        List<String> errors = exception.getBindingResult().getFieldErrors()
                .stream().map(field -> field.getField() + " " + field.getDefaultMessage()).toList();
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST)
                .message(String.join(", ", errors))
                .build();

        return ResponseEntity.status(exception.getStatusCode()).body(errorResponse);
    }
}
