package smr.shop.libs.common.dto.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import smr.shop.libs.common.messaging.BaseMessageModel;

import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ProductDeleteMessageModel implements BaseMessageModel {

    private Long id;

    private Long shopId;

    private Long categoryId;

    private Long brandId;

    private String name;

    private String slug;

    private String description;

    private String thumbnail;

    private List<String> imageIds;

    private List<String> tags;

    private Double price;

    private Double minPrice;

    private Double maxPrice;

    private Double shippingPrice;

    private Float rating;

//    private ProductStatus status;

}