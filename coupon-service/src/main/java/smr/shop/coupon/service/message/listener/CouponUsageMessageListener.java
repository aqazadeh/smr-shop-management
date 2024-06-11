package smr.shop.coupon.service.message.listener;

import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class CouponUsageMessageListener implements MessageListener<UseCouponMessageModel> {

    private final CouponUsageService couponUsageService;

    public CouponUsageMessageListener(CouponUsageService couponUsageService) {
        this.couponUsageService = couponUsageService;
    }

    @Override
    @KafkaListener(topics = MessagingConstants.COUPON_USAGE_TOPIC, groupId = MessagingConstants.COUPON_SERVICE_GROUP)
    public void receive(
            @Payload UseCouponMessageModel message,
            @Header(KafkaHeaders.RECEIVED_KEY) String key,
            @Header(KafkaHeaders.RECEIVED_PARTITION) Integer partition,
            @Header(KafkaHeaders.OFFSET) Long offset) {

        log.info("{} number of ProductStockMessageModel response received with key:{}, partition:{} and offset: {}",
                message.toString(),
                key,
                partition.toString(),
                offset.toString());
        couponUsageService.createCouponUsage(message);
    }
}
