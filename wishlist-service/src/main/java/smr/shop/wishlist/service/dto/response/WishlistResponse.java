package smr.shop.wishlist.service.dto.response;

import lombok.Builder;
import lombok.Value;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;

import java.time.ZonedDateTime;
import java.util.UUID;

@Value
@Builder
public class WishlistResponse {
    @Id
    UUID id;
    Long userId;
    Long productId;
    @CreatedDate
    ZonedDateTime createdAt;

}
