package smr.shop.courier.service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Value;
import smr.shop.courier.service.model.valueobject.CourierActiveType;

@Value
public class CourierUpdateRequest {
    @NotBlank
    @Size(min = 4)
    String name;
    @NotBlank
    String surname;
    Float rating;
    CourierActiveType activeType;
    Boolean isAccepted;
}
