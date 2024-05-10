package smr.shop.product.service.dto.response;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

/**
 * Author: Ali Gadashov
 * Version: v1.0
 * Date: 5/11/2024
 * Time: 2:53 AM
 */
@Value
@Builder
public class ProductDiscountResponse {

    UUID discountId;

    Double discountPrice;
}
