package smr.shop.brand.service.dto.response;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class BrandResponse {
    Long id;
    String name;
    String slug;
    String description;
    String imageUrl;
}
