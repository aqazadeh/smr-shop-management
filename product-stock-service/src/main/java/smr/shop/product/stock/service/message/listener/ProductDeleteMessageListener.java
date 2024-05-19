package smr.shop.product.stock.service.message.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import smr.shop.libs.common.constant.MessagingConstants;
import smr.shop.libs.common.dto.message.ProductMessageModel;
import smr.shop.libs.common.messaging.listener.MessageListener;
import smr.shop.product.stock.service.service.ProductStockService;

@Component
public class ProductDeleteMessageListener implements MessageListener<ProductMessageModel> {

    private final ProductStockService productStockService;

    public ProductDeleteMessageListener(ProductStockService productStockService) {
        this.productStockService = productStockService;
    }

    @Override
    @KafkaListener(topics = MessagingConstants.PRODUCT_DELETE_TOPIC, groupId = MessagingConstants.PRODUCT_PRODUCT_STOCK_DELETE_GROUP)
    public void receive(ProductMessageModel message, String key, Integer partition, Long offset) {
//        productStockService.deleteByProductId(message.getId());
    }
}
