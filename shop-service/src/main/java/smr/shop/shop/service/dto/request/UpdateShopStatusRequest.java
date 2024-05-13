package smr.shop.shop.service.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Value;
import smr.shop.shop.service.model.valueobject.ShopStatus;

@Value
public class UpdateShopStatusRequest {
    @NotBlank ShopStatus status;
}
