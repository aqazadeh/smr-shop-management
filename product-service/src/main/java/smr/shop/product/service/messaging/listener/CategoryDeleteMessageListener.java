package smr.shop.product.service.messaging.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import smr.shop.libs.common.constant.MessagingConstants;
import smr.shop.libs.common.dto.message.CategoryMessageModel;
import smr.shop.libs.common.messaging.listener.MessageListener;
import smr.shop.product.service.service.ProductService;

@Component
public class CategoryDeleteMessageListener implements MessageListener<CategoryMessageModel> {
    private final ProductService productService;

    public CategoryDeleteMessageListener(ProductService productService) {
        this.productService = productService;
    }

    @Override
    @KafkaListener(topics = MessagingConstants.CATEGORY_DELETE_TOPIC, groupId = MessagingConstants.PRODUCT_SERVICE_CATEGORY_DELETE_GROUP)
    public void receive(@Payload CategoryMessageModel message,
                        @Header(KafkaHeaders.RECEIVED_KEY) String key,
                        @Header(KafkaHeaders.RECEIVED_PARTITION) Integer partition,
                        @Header(KafkaHeaders.OFFSET) Long offset) {
        productService.deleteProductsByCategory(message);
    }
}
