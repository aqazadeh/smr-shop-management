package smr.shop.libs.common.dto.response;


public class EmptyResponse {
    private String message;

    public EmptyResponse() {

    }
    public EmptyResponse(String message) {
        this.message = message;
    }

    private EmptyResponse(Builder builder) {
        setMessage(builder.message);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {
        private String message;

        private Builder() {
        }

        public static Builder builder() {
            return new Builder();
        }

        public Builder message(String message) {
            this.message = message;
            return this;
        }

        public EmptyResponse build() {
            return new EmptyResponse(this);
        }
    }
}
