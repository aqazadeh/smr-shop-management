package smr.shop.flash.service.dto.response;

import lombok.Builder;
import lombok.Value;

import java.time.ZonedDateTime;

@Value
@Builder
public class FlashResponse {
    Long id;
    String title;
    String slug;
    ZonedDateTime startDate;
    ZonedDateTime endDate;
    Boolean isActive;
}
