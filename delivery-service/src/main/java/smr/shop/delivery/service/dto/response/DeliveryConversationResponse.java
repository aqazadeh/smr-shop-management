package smr.shop.delivery.service.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class DeliveryConversationResponse {
    UUID id;
    @JsonProperty("user_id") UUID userId;
    @JsonProperty("delivery_id") Long deliveryId;
    String message;
}
