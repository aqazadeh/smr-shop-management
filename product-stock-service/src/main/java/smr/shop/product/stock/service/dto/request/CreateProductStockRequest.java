package smr.shop.product.stock.service.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

@Value
public class CreateProductStockRequest {
    @NotNull
    @Min(value = 1)
    Long productId;
    @NotBlank
    String attributeName;
    @NotNull
    @Min(value = 1)
    Integer quantity;
}