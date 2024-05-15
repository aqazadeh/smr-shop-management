package smr.shop.coupon.service.service;

import smr.shop.libs.common.dto.message.UseCouponMessageModel;

import java.util.UUID;

/**
 * Author: Ali Gadashov
 * Version: v1.0
 * Date: 5/9/2024
 * Time: 4:49 PM
 */

public interface CouponUsageService {


    void createCouponUsage(UseCouponMessageModel useCouponMessageModel);

    Boolean getCouponUsage(UUID couponId, UUID userId);
}
