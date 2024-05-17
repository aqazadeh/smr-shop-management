package smr.shop.shop.service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Value;
import org.springframework.validation.annotation.Validated;

import java.util.UUID;

@Value
@Validated
public class CreateShopRequest {
    @NotBlank
    @Size(min = 4)
    String name;
    @NotBlank
    String slug;
    @NotBlank
    UUID imageId;
    @NotBlank
    String phone;
    @NotBlank
    String description;
    @NotBlank
    CreateShopAddressRequest address;
}
