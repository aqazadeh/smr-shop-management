package smr.shop.product.service.messaging.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import smr.shop.libs.common.constant.MessagingConstants;
import smr.shop.libs.common.dto.message.RatingMessageModel;
import smr.shop.libs.common.messaging.listener.MessageListener;
import smr.shop.product.service.service.ProductService;

@Component
@Slf4j
public class ProductRatingChangeMessageListener implements MessageListener<RatingMessageModel> {
    private final ProductService productService;

    public ProductRatingChangeMessageListener(ProductService productService) {
        this.productService = productService;
    }

    @Override
    @KafkaListener(topics = MessagingConstants.PRODUCT_REVIEW_UPDATE_TOPIC, groupId = MessagingConstants.PRODUCT_SERVICE_GROUP)
    public void receive(@Payload RatingMessageModel message,
                        @Header(KafkaHeaders.RECEIVED_KEY) String key,
                        @Header(KafkaHeaders.RECEIVED_PARTITION) Integer partition,
                        @Header(KafkaHeaders.OFFSET) Long offset) {

        log.info("{} number of BrandMessageModel response received with key:{}, partition:{} and offset: {}",
                message.toString(),
                key,
                partition.toString(),
                offset.toString());

        productService.updateProductRating(message);
    }
}
