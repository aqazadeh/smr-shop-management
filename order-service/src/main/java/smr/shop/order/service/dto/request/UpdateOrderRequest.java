package smr.shop.order.service.dto.request;

import lombok.Value;
import smr.shop.order.service.model.valueobject.OrderAddress;

import java.util.UUID;

@Value
public class UpdateOrderRequest {
    UUID userId;
    OrderAddress address;
    Double shippingCost;
}
