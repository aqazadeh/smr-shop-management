package smr.shop.product.stock.service.mapper;

import org.springframework.stereotype.Component;
import smr.shop.libs.common.constant.ServiceConstants;
import smr.shop.libs.common.dto.message.ProductStockMessageModel;
import smr.shop.libs.grpc.product.stock.ProductStockGrpcResponse;
import smr.shop.product.stock.service.dto.request.CreateProductStockRequest;
import smr.shop.product.stock.service.dto.request.UpdateProductStockRequest;
import smr.shop.product.stock.service.dto.response.ProductStockResponse;
import smr.shop.product.stock.service.model.ProductStockEntity;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;

@Component
public class ProductStockServiceMapper {
    public ProductStockResponse productStockEntityToProductStockResponse(ProductStockEntity productStockEntity) {
        return ProductStockResponse.builder()
                .stockId(productStockEntity.getId())
                .productId(productStockEntity.getProductId())
                .attributeName(productStockEntity.getAttributeName())
                .quantity(productStockEntity.getQuantity())
                .build();
    }

    public ProductStockEntity createProductStockRequestToProductStockEntity(CreateProductStockRequest request) {
        return ProductStockEntity.builder()
                .productId(request.getProductId())
                .attributeName(request.getAttributeName())
                .id(UUID.randomUUID())
                .createdAt(ZonedDateTime.now(ZoneId.of(ServiceConstants.UTC)))
                .quantity(request.getQuantity())
                .build();
    }

    public void updateProductStockRequestToProductStockEntity(ProductStockEntity productStockEntity, UpdateProductStockRequest request) {
        if (request.getAttributeName() != null)
            productStockEntity.setAttributeName(request.getAttributeName());
        if (request.getQuantity() != null)
            productStockEntity.setQuantity(request.getQuantity());
    }

    public ProductStockEntity productStockMessageModelToProductStockEntity(ProductStockMessageModel request) {
        return ProductStockEntity.builder()
                .productId(request.getProductId())
                .attributeName(request.getAttributeName())
                .quantity(request.getQuantity())
                .build();
    }

    public ProductStockGrpcResponse productStockEntityToProductStockGrpcResponse(ProductStockEntity productStockEntity) {
        return ProductStockGrpcResponse.newBuilder()
                .setId(productStockEntity.getProductId().toString())
                .setProductId(productStockEntity.getProductId())
                .setName(productStockEntity.getAttributeName())
                .setQuantity(productStockEntity.getQuantity())
                .build();
    }
}