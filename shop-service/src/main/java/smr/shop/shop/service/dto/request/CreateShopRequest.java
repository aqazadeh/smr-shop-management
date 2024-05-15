package smr.shop.shop.service.dto.request;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Value;
import smr.shop.shop.service.model.valueobject.ShopAddress;

@Value
public class CreateShopRequest {
    @NotBlank UUID userId;
    @NotBlank @Size(min = 4) String name;
    @NotBlank String slug;
    @NotBlank String logo;
    @NotBlank String phone;
    @NotBlank ShopAddress address;
    @NotBlank @Lob String description;
}
