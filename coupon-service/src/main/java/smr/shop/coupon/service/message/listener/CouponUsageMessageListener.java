package smr.shop.coupon.service.message.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import smr.shop.coupon.service.service.CouponUsageService;
import smr.shop.libs.common.constant.MessagingConstants;
import smr.shop.libs.common.dto.message.UseCouponMessageModel;
import smr.shop.libs.common.messaging.listener.MessageListener;

@Component
public class CouponUsageMessageListener implements MessageListener<UseCouponMessageModel> {

    private final CouponUsageService couponUsageService;

    public CouponUsageMessageListener(CouponUsageService couponUsageService) {
        this.couponUsageService = couponUsageService;
    }

    @Override
    @KafkaListener(topics = MessagingConstants.COUPON_USAGE_TOPIC, groupId = MessagingConstants.COUPON_SERVICE_COUPON_USAGE_GROUP )
    public void receive(
            @Payload UseCouponMessageModel message,
            @Header(KafkaHeaders.RECEIVED_KEY) String key,
            @Header(KafkaHeaders.RECEIVED_PARTITION) Integer partition,
            @Header(KafkaHeaders.OFFSET) Long offset) {

        couponUsageService.createCouponUsage(message);
    }
}
