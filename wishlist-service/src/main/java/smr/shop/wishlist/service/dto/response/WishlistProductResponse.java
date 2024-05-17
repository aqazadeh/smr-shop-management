package smr.shop.wishlist.service.dto.response;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;

@Value
@Builder
public class WishlistProductResponse {
    Long id;
    String slug;
    String name;
    BigDecimal price;
    Double discountPrice;
    String thumbnail;
}
