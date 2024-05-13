package smr.shop.wishlist.service.dto.response;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class WishlistProductResponse {
    Long id;
    String slug;
    String name;
    Double price;
    Double discountPrice;
    String thumbnail;
}
