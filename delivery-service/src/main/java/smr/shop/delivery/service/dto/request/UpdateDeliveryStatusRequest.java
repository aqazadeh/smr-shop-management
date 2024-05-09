package smr.shop.delivery.service.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Value;
import smr.shop.delivery.service.model.valueobject.DeliveryStatus;

@Value
public class UpdateDeliveryStatusRequest {
    @NotBlank DeliveryStatus status;
}
