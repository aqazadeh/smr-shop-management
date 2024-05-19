package smr.shop.product.stock.service.dto.response;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class ProductStockResponse {
     UUID stockId;
     Long productId;
     String attributeName;
     Integer quantity;
}