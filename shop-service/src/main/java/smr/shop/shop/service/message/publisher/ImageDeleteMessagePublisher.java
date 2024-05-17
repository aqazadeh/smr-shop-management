package smr.shop.shop.service.message.publisher;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import smr.shop.libs.common.constant.MessagingConstants;
import smr.shop.libs.common.dto.message.ImageDeleteMessageModel;
import smr.shop.libs.common.messaging.publisher.MessagePublisher;

import java.util.UUID;

@Component
public class ImageDeleteMessagePublisher implements MessagePublisher<ImageDeleteMessageModel> {
    private final KafkaTemplate<String, ImageDeleteMessageModel> kafkaTemplate;

    public ImageDeleteMessagePublisher(KafkaTemplate<String, ImageDeleteMessageModel> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publish(ImageDeleteMessageModel messageModel) {
        kafkaTemplate.send(MessagingConstants.IMAGE_DELETE_TOPIC, UUID.randomUUID().toString(), messageModel);
    }
}
