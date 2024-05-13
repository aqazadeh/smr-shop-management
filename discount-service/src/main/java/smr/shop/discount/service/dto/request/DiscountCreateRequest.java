package smr.shop.discount.service.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Value;
import smr.shop.discount.service.model.valueobject.DiscountType;

@Value
public class DiscountCreateRequest {
    @NotBlank
    Long productId;

    @NotBlank
    DiscountType type;

    Float percent;

    Double amount;

}
