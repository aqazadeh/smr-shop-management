package smr.shop.product.service.messaging.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import smr.shop.libs.common.constant.MessagingConstants;
import smr.shop.libs.common.dto.message.DiscountMessageModel;
import smr.shop.libs.common.messaging.listener.MessageListener;
import smr.shop.product.service.service.ProductService;

@Component
public class DiscountExpiredMessageListener implements MessageListener<DiscountMessageModel> {
    private final ProductService productService;

    public DiscountExpiredMessageListener(ProductService productService) {
        this.productService = productService;
    }

    @Override
    @KafkaListener(topics = MessagingConstants.DISCOUNT_DELETE_TOPIC, groupId = MessagingConstants.PRODUCT_SERVICE_DISCOUNT_DELETE_GROUP)
    public void receive(@Payload DiscountMessageModel message,
                        @Header(KafkaHeaders.RECEIVED_KEY) String key,
                        @Header(KafkaHeaders.RECEIVED_PARTITION) Integer partition,
                        @Header(KafkaHeaders.OFFSET) Long offset) {
        productService.removeProductDiscount(message);
    }
}
