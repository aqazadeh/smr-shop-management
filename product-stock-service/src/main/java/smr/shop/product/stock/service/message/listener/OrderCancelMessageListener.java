package smr.shop.product.stock.service.message.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import smr.shop.libs.common.constant.MessagingConstants;
import smr.shop.libs.common.dto.message.OrderMessageModel;
import smr.shop.libs.common.messaging.listener.MessageListener;
import smr.shop.product.stock.service.service.ProductStockService;

@Component
public class OrderCancelMessageListener implements MessageListener<OrderMessageModel> {
    private final ProductStockService productStockService;

    public OrderCancelMessageListener(ProductStockService productStockService) {
        this.productStockService = productStockService;
    }

    @Override
    @KafkaListener(topics = MessagingConstants.ORDER_CANCEL_TOPIC, groupId = MessagingConstants.ORDER_PRODUCT_STOCK_GROUP )
    public void receive(OrderMessageModel message, String key, Integer partition, Long offset) {
        productStockService.updateQuantityIncrease(message);
    }
}
