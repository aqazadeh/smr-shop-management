package smr.shop.coupon.service.message.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import smr.shop.coupon.service.service.CouponService;
import smr.shop.libs.common.constant.MessagingConstants;
import smr.shop.libs.common.dto.message.ShopMessageModel;
import smr.shop.libs.common.messaging.listener.MessageListener;

@Component
public class ShopDeleteMessageListener implements MessageListener<ShopMessageModel> {
    private final CouponService couponService;

    public ShopDeleteMessageListener(CouponService couponService) {
        this.couponService = couponService;
    }

    @Override
    @KafkaListener(topics = MessagingConstants.SHOP_STATUS_CHANGE_TOPIC, groupId = MessagingConstants.COUPON_SERVICE_SHOP_STATUS_CHANGE_GROUP)
    public void receive(ShopMessageModel message, String key, Integer partition, Long offset) {
        couponService.deleteCoupons(message);
    }
}
