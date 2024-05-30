package smr.shop.flash.service.dto.request;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Value;

import java.time.ZonedDateTime;

@Value
public class FlashUpdateRequest {

    @NotBlank
    @Size(min = 5)
    String title;

    @Size(min = 5)
    @NotBlank
    String slug;

    @NotBlank
    @Future
    ZonedDateTime startDate;

    @NotBlank
    @Future
    ZonedDateTime endDate;
}
