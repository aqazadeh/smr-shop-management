package smr.shop.product.service.dto.response;

import lombok.Builder;
import lombok.Value;

/**
 * Author: Ali Gadashov
 * Version: v1.0
 * Date: 5/11/2024
 * Time: 2:46 AM
 */
@Value
@Builder
public class ProductShopResponse {

    String shopSlug;

    String shopName;

    String shopThumbNail;

}
