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
public class DiscountMessageModel implements BaseMessageModel {

    private UUID id;

    private Long productId;

//    private DiscountType type;

    private Float percent;

    private Double amount;
}
