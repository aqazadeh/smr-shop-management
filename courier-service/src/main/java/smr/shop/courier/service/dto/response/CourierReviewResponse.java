package smr.shop.courier.service.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class CourierReviewResponse {
    UUID id;
    @JsonProperty("order_id") UUID orderId;
    @JsonProperty("user_id") UUID userId;
    @JsonProperty("courier_id") Long courierId;
    @JsonProperty("review_score") Byte reviewScore;
}
