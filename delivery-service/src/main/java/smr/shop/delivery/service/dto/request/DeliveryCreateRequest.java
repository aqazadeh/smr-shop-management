package smr.shop.delivery.service.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Value;

import java.util.UUID;

@Value
public class DeliveryCreateRequest {
    @JsonProperty("courier_id") @NotBlank Long courierId;
    @JsonProperty("order_id")  @NotBlank UUID orderId;
}
