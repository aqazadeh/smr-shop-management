package smr.shop.brand.service.messaging.publisher;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import smr.shop.libs.common.constant.MessagingConstants;
import smr.shop.libs.common.dto.message.BrandImageDeleteMessageModel;
import smr.shop.libs.common.messaging.publisher.MessagePublisher;

import java.util.UUID;

@Component
public class ImageDeleteMessagePublisher implements MessagePublisher<BrandImageDeleteMessageModel> {

    private final KafkaTemplate<String, BrandImageDeleteMessageModel> kafkaTemplate;

    public ImageDeleteMessagePublisher(KafkaTemplate<String, BrandImageDeleteMessageModel> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publish(BrandImageDeleteMessageModel messageModel) {
        kafkaTemplate.send(MessagingConstants.IMAGE_DELETE_TOPIC, UUID.randomUUID().toString(), messageModel);
    }
}
