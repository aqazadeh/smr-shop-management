package smr.shop.delivery.service.dto.request;

import lombok.Value;
import smr.shop.delivery.service.model.valueobject.DeliveryStatus;

import java.util.UUID;

@Value
public class DeliveryCreateRequest {
    Long courierId;
    UUID orderId;
}
