package smr.shop.order.service.dto.request;

import lombok.Value;
import smr.shop.order.service.model.valueobject.OrderStatus;

@Value
public class UpdateOrderStatusRequest {
    OrderStatus orderStatus;
}
