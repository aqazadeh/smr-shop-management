package smr.shop.delivery.service.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Value;

import java.util.UUID;

@Value
public class CreateDeliveryConverstaionRequest {
    @JsonProperty("user_id") @NotBlank UUID userId;
    @JsonProperty("delivery_id") @NotBlank Long deliveryId;
    @NotBlank String message;
}
