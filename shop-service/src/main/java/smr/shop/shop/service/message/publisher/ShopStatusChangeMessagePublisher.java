package smr.shop.shop.service.message.publisher;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import smr.shop.libs.common.constant.MessagingConstants;
import smr.shop.libs.common.dto.message.ShopMessageModel;
import smr.shop.libs.common.messaging.publisher.MessagePublisher;

import java.util.UUID;

@Component
public class ShopStatusChangeMessagePublisher implements MessagePublisher<ShopMessageModel> {

    private KafkaTemplate<String, ShopMessageModel> kafkaTemplate;

    @Override
    public void publish(ShopMessageModel messageModel) {
        kafkaTemplate.send(MessagingConstants.SHOP_STATUS_CHANGE_TOPIC, UUID.randomUUID().toString(), messageModel);
    }
}
