package smr.shop.discount.service.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;

import java.time.ZonedDateTime;
import java.util.UUID;

@Value
@Builder
public class DiscountResponse {
    UUID id;

    Long shopId;

    @JsonProperty
    ZonedDateTime expireDate;

    Double amount;
}
