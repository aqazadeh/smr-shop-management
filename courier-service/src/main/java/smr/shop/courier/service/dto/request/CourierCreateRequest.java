package smr.shop.courier.service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.util.UUID;

@Value
public class CourierCreateRequest {
    @NotBlank
    Long userId;
    @NotBlank
    UUID imageId;
    @NotBlank
    @Size(min = 4)
    String name;
    @NotBlank
    String surname;
}
