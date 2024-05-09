package smr.shop.courier.service.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Value;
import smr.shop.courier.service.model.valueobject.CourierActiveType;

@Value
public class UpdateCourierActiveTypeRequest {
    @NotBlank
    CourierActiveType status;
}
