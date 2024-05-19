package smr.shop.product.stock.service.message.listener;

import smr.shop.libs.common.dto.message.ProductStockMessageModel;
import smr.shop.libs.common.messaging.listener.MessageListener;
import smr.shop.product.stock.service.service.ProductStockService;

import java.util.List;

public class ProductStockCreateListener implements MessageListener<ProductStockMessageModel> {
    private final ProductStockService productStockService;

    public ProductStockCreateListener(ProductStockService productStockService) {
        this.productStockService = productStockService;
    }

    @Override
    public void receive(ProductStockMessageModel message, String key, Integer partition, Long offset) {
        productStockService.createAll(List.of(message));
    }
}
