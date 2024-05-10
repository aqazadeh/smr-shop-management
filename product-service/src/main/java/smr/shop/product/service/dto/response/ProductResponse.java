package smr.shop.product.service.dto.response;

import lombok.Builder;
import lombok.Value;

import java.util.List;

/**
 * Author: Ali Gadashov
 * Version: v1.0
 * Date: 5/11/2024
 * Time: 12:26 AM
 */
@Value
@Builder
public class ProductResponse {

    Long id;

    ProductShopResponse shopDetail;

    ProductCategoryResponse category;

    ProductBrandResponse brand;

    ProductDiscountResponse discount;

    String productName;

    String productSlug;

    String productDescription;

    String productThumbnail;

    List<String> images;

    List<String> tags;

    Double price;

    Double shippingPrice;

    Float rating;

}
