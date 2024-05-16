package smr.shop.product.stock.service.mapper;

import org.springframework.stereotype.Component;
import smr.shop.libs.common.constant.ServiceConstants;
import smr.shop.product.stock.service.dto.request.CreateProductStockRequest;
import smr.shop.product.stock.service.dto.request.UpdateProductStockRequest;
import smr.shop.product.stock.service.dto.response.GetProductStockResponse;
import smr.shop.product.stock.service.model.ProductStock;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;

@Component
public class ProductStockServiceMapper {
    public GetProductStockResponse mapToResponse(ProductStock productStock) {
        return GetProductStockResponse.builder()
                .productId(productStock.getProductId())
                .attributeName(productStock.getAttributeName())
                .quantity(productStock.getQuantity())
                .build();
    }

    public ProductStock mapToProductStock(CreateProductStockRequest request) {
        return ProductStock.builder()
                .productId(request.getProductId())
                .attributeName(request.getAttributeName())
                .id(UUID.randomUUID())
                .createdAt(ZonedDateTime.now(ZoneId.of(ServiceConstants.UTC)))
                .quantity(request.getQuantity())
                .build();
    }

    public void mapForUpdate(ProductStock productStock, UpdateProductStockRequest request) {
        if (request.getAttributeName() != null)
            productStock.setAttributeName(request.getAttributeName());
        if (request.getQuantity() != null)
            productStock.setQuantity(request.getQuantity());
        if (request.getProductId() != null)
            productStock.setProductId(request.getProductId());
    }
}