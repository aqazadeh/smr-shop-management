package smr.shop.product.service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

/**
 * Author: Ali Gadashov
 * Version: v1.0
 * Date: 5/11/2024
 * Time: 12:43 AM
 */

@Value
public class ProductStockRequest {

    @NotBlank
    String attributeName;

    @NotNull
    Integer quantity;



}
