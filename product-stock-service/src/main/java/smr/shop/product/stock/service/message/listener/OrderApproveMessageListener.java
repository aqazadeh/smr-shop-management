package smr.shop.product.stock.service.message.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import smr.shop.libs.common.constant.MessagingConstants;
import smr.shop.libs.common.dto.message.OrderMessageModel;
import smr.shop.libs.common.messaging.listener.MessageListener;
import smr.shop.product.stock.service.service.ProductStockService;

@Component
@Slf4j
public class OrderApproveMessageListener implements MessageListener<OrderMessageModel> {

    private final ProductStockService productStockService;

    public OrderApproveMessageListener(ProductStockService productStockService) {
        this.productStockService = productStockService;
    }

    @Override
    @KafkaListener(topics = MessagingConstants.ORDER_APPROVE_TOPIC, groupId = MessagingConstants.ORDER_PRODUCT_STOCK_GROUP )
    public void receive(OrderMessageModel message, String key, Integer partition, Long offset) {
        log.info("");
        productStockService.updateQuantityDecrease(message);
    }
}
