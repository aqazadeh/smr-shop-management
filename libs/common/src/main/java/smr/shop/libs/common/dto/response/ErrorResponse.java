package smr.shop.libs.common.dto.response;

import org.springframework.http.HttpStatus;

public class ErrorResponse {
    private final int code;
    private final String message;

    public ErrorResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private ErrorResponse(ErrorResponseBuilder builder) {
        code = builder.code;
        message = builder.message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static ErrorResponseBuilder builder() {
        return new ErrorResponseBuilder();
    }

    public static class ErrorResponseBuilder {
        private int code;
        private String message;

        public ErrorResponseBuilder code(HttpStatus code) {
            this.code = code.value();
            return this;
        }

        public ErrorResponseBuilder message(String message) {
            this.message = message;
            return this;
        }

        public ErrorResponse build() {
            return new ErrorResponse(this);
        }
    }
}
