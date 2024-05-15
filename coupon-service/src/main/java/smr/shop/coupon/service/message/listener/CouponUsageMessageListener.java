package smr.shop.coupon.service.message.listener;

import org.springframework.stereotype.Component;
import smr.shop.coupon.service.service.CouponService;
import smr.shop.coupon.service.service.CouponUsageService;
import smr.shop.libs.common.dto.message.UseCouponMessageModel;
import smr.shop.libs.common.messaging.listener.MessageListener;

@Component
public class CouponUsageMessageListener implements MessageListener<UseCouponMessageModel> {

    private final CouponUsageService couponUsageService;

    public CouponUsageMessageListener(CouponUsageService couponUsageService) {
        this.couponUsageService = couponUsageService;
    }

    @Override
    public void receive(UseCouponMessageModel message, String key, Integer partition, Long offset) {
        couponUsageService.createCouponUsage(message);
    }
}
