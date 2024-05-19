package smr.shop.category.brand.service.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BrandResponse {
    Long id;
    String name;
    String slug;
    String description;
    String imageUrl;
}
