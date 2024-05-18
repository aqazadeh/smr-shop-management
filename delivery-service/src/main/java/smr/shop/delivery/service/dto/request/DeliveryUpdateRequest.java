package smr.shop.delivery.service.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;
import smr.shop.delivery.service.model.valueobject.DeliveryStatus;

import java.util.UUID;

@Value
public class DeliveryUpdateRequest {
    @JsonProperty("courier_id") Long courierId;
    @JsonProperty("order_id") UUID orderId;
    DeliveryStatus status;
}
