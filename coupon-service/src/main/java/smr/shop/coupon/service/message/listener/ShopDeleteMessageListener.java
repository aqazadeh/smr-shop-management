package smr.shop.coupon.service.message.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import smr.shop.coupon.service.service.CouponService;
import smr.shop.libs.common.constant.MessagingConstants;
import smr.shop.libs.common.dto.message.ShopMessageModel;
import smr.shop.libs.common.messaging.listener.MessageListener;

@Component
@Slf4j
public class ShopDeleteMessageListener implements MessageListener<ShopMessageModel> {
    private final CouponService couponService;

    public ShopDeleteMessageListener(CouponService couponService) {
        this.couponService = couponService;
    }

    @Override
    @KafkaListener(topics = MessagingConstants.SHOP_STATUS_CHANGE_TOPIC, groupId = MessagingConstants.COUPON_SERVICE_GROUP)
    public void receive(@Payload ShopMessageModel message,
                        @Header(KafkaHeaders.RECEIVED_KEY) String key,
                        @Header(KafkaHeaders.RECEIVED_PARTITION) Integer partition,
                        @Header(KafkaHeaders.OFFSET) Long offset) {

        log.info("{} number of ProductStockMessageModel response received with key:{}, partition:{} and offset: {}",
                message.toString(),
                key,
                partition.toString(),
                offset.toString());
        couponService.deleteCoupons(message);
    }
}
