package smr.shop.product.stock.service.dto.response;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ProductStockResponse {
     Long productId;
     String attributeName;
     Integer quantity;
}