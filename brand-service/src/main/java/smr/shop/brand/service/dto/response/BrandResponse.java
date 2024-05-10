package smr.shop.brand.service.dto.response;

import lombok.*;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BrandResponse {
    Long id;
    String name;
    String slug;
    String description;
    String imageUrl;
}
