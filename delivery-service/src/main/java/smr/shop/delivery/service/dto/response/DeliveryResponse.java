package smr.shop.delivery.service.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import smr.shop.delivery.service.model.valueobject.DeliveryStatus;

import java.util.UUID;

@Value
@Builder
public class DeliveryResponse {
    Long id;
    @JsonProperty("courier_id") Long courierId;
    @JsonProperty("order_id") UUID orderId;
    DeliveryStatus status;
}
