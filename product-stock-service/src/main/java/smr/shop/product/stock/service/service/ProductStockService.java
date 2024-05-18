package smr.shop.product.stock.service.service;

import smr.shop.libs.common.dto.message.OrderMessageModel;
import smr.shop.product.stock.service.dto.request.CreateProductStockRequest;
import smr.shop.product.stock.service.dto.request.UpdateProductStockRequest;
import smr.shop.product.stock.service.dto.response.ProductStockResponse;

import java.util.List;
import java.util.UUID;

public interface ProductStockService {

    ProductStockResponse getById(UUID stockId);

    List<ProductStockResponse> getByProductId(Long productId);

    void create(CreateProductStockRequest request);

    void createAll(List<CreateProductStockRequest> productStockRequests);

    void update(UUID stockId, UpdateProductStockRequest request);

    void updateQuantity(OrderMessageModel);

    void delete(UUID stockId);

    void deleteByProductId(Long stockId);
}
