package smr.shop.discount.service.dto.response;

import lombok.Builder;
import lombok.Value;
import smr.shop.discount.service.model.valueobject.DiscountType;

@Value
@Builder
public class DiscountResponse {
    Long id;
    Long productId;
    DiscountType type;
    Float percent;
    Double amount;


}
