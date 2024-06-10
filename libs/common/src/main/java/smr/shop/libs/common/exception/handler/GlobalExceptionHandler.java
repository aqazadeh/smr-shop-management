package smr.shop.libs.common.exception.handler;

import io.grpc.StatusRuntimeException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
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
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {

        log.error(exception.getMessage(), exception);

        List<String> errors = exception.getBindingResult().getFieldErrors()
                .stream().map(field -> field.getField() + " " + field.getDefaultMessage()).toList();
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(HttpStatus.BAD_REQUEST)
                .message(String.join(", ", errors))
                .build();

        return ResponseEntity.status(exception.getStatusCode()).body(errorResponse);
    }

    @ExceptionHandler(StatusRuntimeException.class)
    public ResponseEntity<ErrorResponse> handleStatusRuntimeException(StatusRuntimeException exception) {
        String message = exception.getMessage();
        String string = message.split(":")[0];
        message = message.replace(string + ":", "").trim();

        log.error(message, exception);
        HttpStatus status;
        switch (exception.getStatus().getCode()) {
            case NOT_FOUND -> status = HttpStatus.NOT_FOUND;
            case INVALID_ARGUMENT -> status = HttpStatus.BAD_REQUEST;
            case UNAVAILABLE -> {
                status = HttpStatus.INTERNAL_SERVER_ERROR;
                message = "Can not to connect grpc server!";
            }
            default -> status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        ErrorResponse errorResponse = ErrorResponse.builder()
                .code(status)
                .message(message)
                .build();

        return ResponseEntity.status(status).body(errorResponse);
    }
}
