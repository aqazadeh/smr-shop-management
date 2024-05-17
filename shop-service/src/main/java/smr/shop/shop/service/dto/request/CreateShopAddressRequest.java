package smr.shop.shop.service.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Value;

@Value
public class CreateShopAddressRequest {
    @NotBlank
    String name;
    @NotBlank
    String street;
    @NotBlank
    String city;
    @NotBlank
    String state;
}
