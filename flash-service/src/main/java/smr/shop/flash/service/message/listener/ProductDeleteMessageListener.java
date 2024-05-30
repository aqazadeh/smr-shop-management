package smr.shop.flash.service.message.listener;

import org.springframework.kafka.annotation.KafkaListener;
import smr.shop.flash.service.service.FlashItemService;
import smr.shop.libs.common.constant.MessagingConstants;
import smr.shop.libs.common.dto.message.ProductMessageModel;
import smr.shop.libs.common.messaging.listener.MessageListener;

public class ProductDeleteMessageListener implements MessageListener<ProductMessageModel> {

    private final FlashItemService flashItemService;

    public ProductDeleteMessageListener(FlashItemService flashItemService) {
        this.flashItemService = flashItemService;
    }

    @Override
    @KafkaListener(topics = MessagingConstants.PRODUCT_DELETE_TOPIC, groupId = MessagingConstants.FLASH_SERVICE_PRODUCT_DELETE_GROUP)
    public void receive(ProductMessageModel message, String key, Integer partition, Long offset) {
        flashItemService.deleteFlash(message);
    }
}
