package smr.shop.cart.service.message.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import smr.shop.cart.service.service.CartService;
import smr.shop.libs.common.constant.MessagingConstants;
import smr.shop.libs.common.dto.message.ProductStockMessageModel;
import smr.shop.libs.common.messaging.listener.MessageListener;

@Component
public class ProductStockRemoveMessageListener implements MessageListener<ProductStockMessageModel> {

    private final CartService cartService;

    public ProductStockRemoveMessageListener(CartService cartService) {
        this.cartService = cartService;
    }

    @Override
    @KafkaListener(topics = MessagingConstants.PRODUCT_STOCK_DELETE_TOPIC, groupId = MessagingConstants.CART_PRODUCT_DELETE_GROUP)
    public void receive(@Payload ProductStockMessageModel message,
                        @Header(KafkaHeaders.RECEIVED_KEY) String key,
                        @Header(KafkaHeaders.RECEIVED_PARTITION) Integer partition,
                        @Header(KafkaHeaders.OFFSET) Long offset) {

        cartService.removeItemByStock(message);
    }
}