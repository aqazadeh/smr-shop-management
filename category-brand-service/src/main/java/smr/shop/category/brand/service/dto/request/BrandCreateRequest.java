package smr.shop.category.brand.service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.util.UUID;

@Value
public class BrandCreateRequest {

    @NotBlank
    @Size(min = 4)
    String name;

    @NotNull
    UUID imageId;

    @NotBlank
    @Size(min = 50)
    String description;

}
