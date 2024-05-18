package smr.shop.delivery.service.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import java.util.UUID;

@Value
public class UpdateDeliveryConversationRequest {
    @JsonProperty("user_id") UUID userId;
    @JsonProperty("delivery_id") Long deliveryId;
    String message;
}
