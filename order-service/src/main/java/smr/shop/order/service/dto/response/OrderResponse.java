package smr.shop.order.service.dto.response;

import lombok.Builder;
import lombok.Value;
import smr.shop.order.service.model.valueobject.OrderAddress;
import smr.shop.order.service.model.valueobject.PaymentStatus;

import java.util.List;
import java.util.UUID;

@Value
@Builder
public class OrderResponse {
    UUID id;
    UUID userId;
    OrderAddress address;
    Double shippingCost;
    List<UUID> couponId;
    PaymentStatus paymentStatus;
}
