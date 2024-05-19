package smr.shop.libs.common.dto.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import smr.shop.libs.common.messaging.BaseMessageModel;

import java.util.UUID;
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class OrderItemMessageModel implements BaseMessageModel {
    private String orderItemId;
    private Long productId;
    private UUID stockId;
    private Integer quantity;
}
