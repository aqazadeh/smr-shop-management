package smr.shop.courier.service.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Value;

import java.util.UUID;

@Value
public class CreateCourierReviewRequest {
    @JsonProperty("order_id") @NotBlank UUID orderId;
    @JsonProperty("user_id") @NotBlank UUID userId;
    @JsonProperty("courier_id") @NotBlank Long courierId;
    @JsonProperty("review_score") @NotBlank Byte reviewScore;
}
