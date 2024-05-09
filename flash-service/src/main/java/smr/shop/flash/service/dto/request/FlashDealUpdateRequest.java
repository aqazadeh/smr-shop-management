package smr.shop.flash.service.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.time.ZonedDateTime;

@Value
public class FlashDealUpdateRequest {

    @NotBlank
    @Size(min = 5)
    String title;

    @Size(min = 5)
    String slug;

    @NotBlank
    @Future
    ZonedDateTime startDate;

    @NotBlank
    @Future
    ZonedDateTime endDate;

    @NotEmpty
    Boolean isActive;
}
