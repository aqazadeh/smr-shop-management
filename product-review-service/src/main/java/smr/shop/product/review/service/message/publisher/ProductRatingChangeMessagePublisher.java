package smr.shop.product.review.service.message.publisher;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import smr.shop.libs.common.constant.MessagingConstants;
import smr.shop.libs.common.dto.message.ProductReviewMessageModel;
import smr.shop.libs.common.messaging.publisher.MessagePublisher;

import java.util.UUID;

@Component
public class ProductRatingChangeMessagePublisher implements MessagePublisher <ProductReviewMessageModel>{

    private final KafkaTemplate<String, ProductReviewMessageModel> kafkaTemplate;

    public ProductRatingChangeMessagePublisher(KafkaTemplate<String, ProductReviewMessageModel> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publish(ProductReviewMessageModel messageModel) {
        kafkaTemplate.send(MessagingConstants.PRODUCT_REVIEW_CHANGE_TOPIC, UUID.randomUUID().toString(), messageModel);
    }
}
