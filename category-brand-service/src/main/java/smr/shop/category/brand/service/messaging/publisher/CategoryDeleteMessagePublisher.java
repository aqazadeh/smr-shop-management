package smr.shop.category.brand.service.messaging.publisher;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import smr.shop.libs.common.constant.MessagingConstants;
import smr.shop.libs.common.dto.message.CategoryMessageModel;
import smr.shop.libs.common.messaging.publisher.MessagePublisher;

import java.util.UUID;

@Component
public class CategoryDeleteMessagePublisher implements MessagePublisher<CategoryMessageModel> {
    private final KafkaTemplate<String, CategoryMessageModel> kafkaTemplate;

    public CategoryDeleteMessagePublisher(KafkaTemplate<String, CategoryMessageModel> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publish(CategoryMessageModel messageModel) {
        kafkaTemplate.send(MessagingConstants.CATEGORY_DELETE_TOPIC, UUID.randomUUID().toString(), messageModel);
    }
}

