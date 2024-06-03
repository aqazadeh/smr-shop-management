package smr.shop.product.service.messaging.publisher;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import smr.shop.libs.common.constant.MessagingConstants;
import smr.shop.libs.common.messaging.publisher.MessagePublisher;
import smr.shop.libs.common.dto.message.StockCreateMessageModel;

import java.util.UUID;

@Component
public class ProductStockCreateMessagePublisher implements MessagePublisher<StockCreateMessageModel> {
    private final KafkaTemplate<String, StockCreateMessageModel> kafkaTemplate;

    public ProductStockCreateMessagePublisher(KafkaTemplate<String, StockCreateMessageModel> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publish(StockCreateMessageModel messageModel) {
        kafkaTemplate.send(MessagingConstants.CREATE_STOCK_TOPIC, UUID.randomUUID().toString(), messageModel);
    }
}
