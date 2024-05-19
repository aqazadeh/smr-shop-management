package smr.shop.category.brand.service.messaging.publisher;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import smr.shop.libs.common.constant.MessagingConstants;
import smr.shop.libs.common.dto.message.BrandMessageModel;
import smr.shop.libs.common.messaging.publisher.MessagePublisher;

import java.util.UUID;

@Component
public class BrandDeleteMessagePublisher implements MessagePublisher<BrandMessageModel> {

    private final KafkaTemplate<String, BrandMessageModel> kafkaTemplate;

    public BrandDeleteMessagePublisher(KafkaTemplate<String, BrandMessageModel> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publish(BrandMessageModel messageModel) {
        kafkaTemplate.send(MessagingConstants.BRAND_DELETE_TOPIC, UUID.randomUUID().toString(), messageModel);
    }
}
