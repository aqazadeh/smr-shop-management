package smr.shop.courier.service.dto.response;

import lombok.Builder;
import lombok.Value;
import smr.shop.courier.service.model.valueobject.CourierActiveType;

import java.util.UUID;

@Value
@Builder
public class CourierResponse {
    Long id;
    UUID userId;
    UUID imageId;
    String name;
    String surname;
    Float rating;
    CourierActiveType activeType;
    Boolean isAccepted;
}
