package smr.shop.product.service.messaging.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import smr.shop.libs.common.constant.MessagingConstants;
import smr.shop.libs.common.dto.message.ShopMessageModel;
import smr.shop.libs.common.messaging.listener.MessageListener;
import smr.shop.product.service.service.ProductService;

@Component
@Slf4j
public class ShopDeleteMessageListener implements MessageListener<ShopMessageModel> {

    private final ProductService productService;

    public ShopDeleteMessageListener(ProductService productService) {
        this.productService = productService;
    }

    @Override
    @KafkaListener(topics = MessagingConstants.SHOP_STATUS_CHANGE_TOPIC, groupId = MessagingConstants.PRODUCT_SERVICE_GROUP)
    public void receive(@Payload ShopMessageModel message,
                        @Header(KafkaHeaders.RECEIVED_KEY) String key,
                        @Header(KafkaHeaders.RECEIVED_PARTITION) Integer partition,
                        @Header(KafkaHeaders.OFFSET) Long offset) {

        log.info("{} number of ShopMessageModel response received with key: {}, partition: {} and offset: {}",
                message.toString(),
                key,
                partition.toString(),
                offset.toString());

        productService.deleteProductsByShop(message);
    }
}
