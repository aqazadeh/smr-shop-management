package smr.shop.coupon.service.message.publisher;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import smr.shop.libs.common.constant.MessagingConstants;
import smr.shop.libs.common.dto.message.CouponMessageModel;
import smr.shop.libs.common.messaging.publisher.MessagePublisher;

import java.util.UUID;

@Component
public class CouponDeleteMessagePublisher implements MessagePublisher<CouponMessageModel> {

    private final KafkaTemplate<String, CouponMessageModel> kafkaTemplate;

    public CouponDeleteMessagePublisher(KafkaTemplate<String, CouponMessageModel> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publish(CouponMessageModel messageModel) {
        kafkaTemplate.send(MessagingConstants.COUPON_DELETE_TOPIC, UUID.randomUUID().toString(), messageModel);
    }
}
