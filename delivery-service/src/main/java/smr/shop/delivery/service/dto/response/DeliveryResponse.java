package smr.shop.delivery.service.dto.response;

import lombok.Builder;
import lombok.Value;
import smr.shop.delivery.service.model.valueobject.DeliveryStatus;

import java.util.UUID;

@Value
@Builder
public class DeliveryResponse {
    Long id;
    Long courierId;
    UUID orderId;
    DeliveryStatus status;
}
