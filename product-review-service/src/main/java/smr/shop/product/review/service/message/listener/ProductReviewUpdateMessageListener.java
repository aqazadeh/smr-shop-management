package smr.shop.product.review.service.message.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import smr.shop.libs.common.constant.MessagingConstants;
import smr.shop.libs.common.dto.message.ProductReviewMessageModel;
import smr.shop.libs.common.messaging.listener.MessageListener;
import smr.shop.product.review.service.service.ProductReviewService;

@Component
public class ProductReviewUpdateMessageListener implements MessageListener<ProductReviewMessageModel> {

    private final ProductReviewService productReviewService;

    public ProductReviewUpdateMessageListener(ProductReviewService productReviewService) {
        this.productReviewService = productReviewService;
    }

    @Override
    @KafkaListener(topics = MessagingConstants.PRODUCT_REVIEW_UPDATE_TOPIC, groupId = MessagingConstants.PRODUCT_REVIEW_SERVICE_UPDATE_REVIEW_GROUP)
    public void receive(ProductReviewMessageModel message, String key, Integer partition, Long offset) {
        productReviewService.calculateRating(message);
    }
}
