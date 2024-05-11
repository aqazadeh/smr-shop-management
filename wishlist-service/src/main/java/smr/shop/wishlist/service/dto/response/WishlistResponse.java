package smr.shop.wishlist.service.dto.response;

import lombok.*;
import org.checkerframework.checker.units.qual.N;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;

import java.time.ZonedDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WishlistResponse {
    UUID id;
    WishlistProductResponse product;
    ZonedDateTime createdAt;
}
