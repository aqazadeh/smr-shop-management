package smr.shop.product.review.service.message.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import smr.shop.libs.common.constant.MessagingConstants;
import smr.shop.libs.common.dto.message.ProductReviewMessageModel;
import smr.shop.libs.common.messaging.listener.MessageListener;
import smr.shop.product.review.service.service.ProductReviewService;

@Component
@Slf4j
public class ProductReviewUpdateMessageListener implements MessageListener<ProductReviewMessageModel> {

    private final ProductReviewService productReviewService;

    public ProductReviewUpdateMessageListener(ProductReviewService productReviewService) {
        this.productReviewService = productReviewService;
    }

    @Override
    @KafkaListener(topics = MessagingConstants.PRODUCT_REVIEW_UPDATE_TOPIC, groupId = MessagingConstants.PRODUCT_REVIEW_SERVICE_GROUP)
    public void receive(@Payload ProductReviewMessageModel message,
                        @Header(KafkaHeaders.RECEIVED_KEY) String key,
                        @Header(KafkaHeaders.RECEIVED_PARTITION) Integer partition,
                        @Header(KafkaHeaders.OFFSET) Long offset) {

        log.info("{} number of ProductStockMessageModel response received with key:{}, partition:{} and offset: {}",
                message.toString(),
                key,
                partition.toString(),
                offset.toString());
        productReviewService.calculateRating(message);
    }
}
