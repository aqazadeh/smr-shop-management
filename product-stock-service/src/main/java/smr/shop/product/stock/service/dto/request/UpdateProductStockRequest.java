package smr.shop.product.stock.service.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Value;

@Value
public class UpdateProductStockRequest {

    @NotBlank
    String attributeName;

    @Min(value = 1)
    Integer quantity;
}