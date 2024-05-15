package smr.shop.order.service.dto.request;

import lombok.Value;
import smr.shop.order.service.model.valueobject.OrderAddress;

import java.util.List;
import java.util.UUID;

@Value
public class CreateOrderRequest {
    UUID userId;
    OrderAddress address;
    Double shippingCost;
    List<UUID> couponId;
}
