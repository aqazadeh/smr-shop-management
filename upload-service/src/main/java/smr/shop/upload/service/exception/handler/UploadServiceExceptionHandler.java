package smr.shop.upload.service.exception.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import smr.shop.libs.common.dto.response.ErrorResponse;
import smr.shop.upload.service.exception.UploadServiceException;

@RestControllerAdvice
@Slf4j
public class UploadServiceExceptionHandler {

    @ExceptionHandler(UploadServiceException.class)
    public ResponseEntity<ErrorResponse> handleUploadServiceException(UploadServiceException exception) {
        log.error(exception.getMessage(), exception);
        ErrorResponse response = ErrorResponse.builder()
                .code(exception.getStatus())
                .message(exception.getMessage())
                .build();
        return ResponseEntity.status(exception.getStatus()).body(response);
    }
}
