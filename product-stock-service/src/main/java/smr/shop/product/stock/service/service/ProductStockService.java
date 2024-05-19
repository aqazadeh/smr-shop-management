package smr.shop.product.stock.service.service;

import smr.shop.libs.common.dto.message.OrderMessageModel;
import smr.shop.libs.common.dto.message.ProductStockMessageModel;
import smr.shop.product.stock.service.dto.request.CreateProductStockRequest;
import smr.shop.product.stock.service.dto.request.UpdateProductStockRequest;
import smr.shop.product.stock.service.dto.response.ProductStockResponse;

import java.util.List;
import java.util.UUID;

public interface ProductStockService {

    ProductStockResponse getById(UUID stockId);

    List<ProductStockResponse> getByProductId(Long productId);

    void create(CreateProductStockRequest request);

    void createAll(List<ProductStockMessageModel> productStockMessageModel);

    void update(UUID stockId, UpdateProductStockRequest request);

    void updateQuantityDecrease(OrderMessageModel orderMessageModel);

    void updateQuantityIncrease(OrderMessageModel message);

    void delete(UUID stockId);

    void deleteByProductId(Long stockId);
}
