package smr.shop.category.brand.service.messaging.publisher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import smr.shop.libs.common.constant.MessagingConstants;
import smr.shop.libs.common.dto.message.UploadMessageModel;
import smr.shop.libs.common.messaging.publisher.MessagePublisher;

import java.util.UUID;

@Component
public class ImageDeleteMessagePublisher implements MessagePublisher<UploadMessageModel> {

    private static final Logger log = LoggerFactory.getLogger(ImageDeleteMessagePublisher.class);
    private final KafkaTemplate<String, UploadMessageModel> kafkaTemplate;

    public ImageDeleteMessagePublisher(KafkaTemplate<String, UploadMessageModel> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publish(UploadMessageModel messageModel) {
        kafkaTemplate.send(MessagingConstants.IMAGE_DELETE_TOPIC, UUID.randomUUID().toString(), messageModel);
    }
}
