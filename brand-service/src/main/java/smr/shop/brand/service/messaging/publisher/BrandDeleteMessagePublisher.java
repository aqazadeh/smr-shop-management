package smr.shop.brand.service.messaging.publisher;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import smr.shop.libs.common.constant.MessagingConstants;
import smr.shop.libs.common.dto.message.BrandDeleteMessageModel;
import smr.shop.libs.common.messaging.publisher.MessagePublisher;

import java.util.UUID;

@Component
public class BrandDeleteMessagePublisher implements MessagePublisher<BrandDeleteMessageModel> {

    private final KafkaTemplate<String, BrandDeleteMessageModel> kafkaTemplate;

    public BrandDeleteMessagePublisher(KafkaTemplate<String, BrandDeleteMessageModel> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publish(BrandDeleteMessageModel messageModel) {
        kafkaTemplate.send(MessagingConstants.brandDeleteTopic, UUID.randomUUID().toString(), messageModel);
    }
}
