package smr.shop.libs.common.dto.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import smr.shop.libs.common.messaging.BaseMessageModel;

import java.util.List;
import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderMessageModel implements BaseMessageModel {
    private UUID id;

    private UUID userId;

//    private OrderAddress address;

    private Double shippingCost;

    private List<UUID> couponId;

//    private PaymentStatus paymentStatus;
    private List<OrderItemMessageModel> items;
}
