package smr.shop.order.service.dto.request;

import lombok.Value;
import smr.shop.order.service.model.valueobject.OrderAddress;

@Value
public class UpdateOrderRequest {
    UUID userId;
    OrderAddress address;
    Double shippingCost;
}
