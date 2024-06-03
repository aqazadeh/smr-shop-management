package smr.shop.category.brand.service.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BrandResponse implements Serializable {
    private Long id;
    private String name;
    private String slug;
    private String description;
    private String imageUrl;
}
