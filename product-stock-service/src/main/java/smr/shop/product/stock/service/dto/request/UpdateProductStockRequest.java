package smr.shop.product.stock.service.dto.request;

import jakarta.validation.constraints.Min;
import lombok.Value;

@Value
public class UpdateProductStockRequest {
    @Min(value = 1)
    Long productId;
    String attributeName;
    @Min(value = 1)
    Integer quantity;
}