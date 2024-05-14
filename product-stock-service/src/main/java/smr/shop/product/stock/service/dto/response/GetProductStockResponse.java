package smr.shop.product.stock.service.dto.response;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GetProductStockResponse {
     Long productId;
     String attributeName;
     Integer quantity;
}