package smr.shop.discount.service.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.time.ZonedDateTime;

@Value
public class DiscountCreateRequest {
    @NotNull
    Double amount;

    @NotNull
    ZonedDateTime expireDate;
}
