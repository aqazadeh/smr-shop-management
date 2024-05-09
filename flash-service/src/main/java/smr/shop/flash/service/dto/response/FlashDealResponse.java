package smr.shop.flash.service.dto.response;

import lombok.Builder;
import lombok.Value;

import java.time.ZonedDateTime;

@Value
@Builder
public class FlashDealResponse {
    Long id;
    String title;
    String slug;
    String imageUrl;
    ZonedDateTime startDate;
    ZonedDateTime endDate;
    Boolean isActive;
}
