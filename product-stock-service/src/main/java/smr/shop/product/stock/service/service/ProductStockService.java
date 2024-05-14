package smr.shop.product.stock.service.service;

import smr.shop.product.stock.service.dto.request.CreateProductStockRequest;
import smr.shop.product.stock.service.dto.request.UpdateProductStockRequest;
import smr.shop.product.stock.service.dto.response.GetProductStockResponse;

import java.util.List;
import java.util.UUID;

public interface ProductStockService {
    CreateProductStockRequest create(CreateProductStockRequest request);
    List<CreateProductStockRequest>createAll(List<CreateProductStockRequest> productStockRequests);
    GetProductStockResponse getById(UUID id);
    GetProductStockResponse getByProductId(Long productId);
    void deleteById(UUID id);
    void updateById(UUID id, UpdateProductStockRequest request);
}
