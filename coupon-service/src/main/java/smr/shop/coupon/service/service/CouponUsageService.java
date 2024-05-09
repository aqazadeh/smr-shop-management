package smr.shop.coupon.service.service;

import smr.shop.coupon.service.dto.response.CouponUsageResponse;
import smr.shop.coupon.service.model.CouponUsageEntity;

import java.util.UUID;

/**
 * Author: Ali Gadashov
 * Version: v1.0
 * Date: 5/9/2024
 * Time: 4:49 PM
 */

public interface CouponUsageService {

    CouponUsageResponse createCouponUsage(UUID couponId);
    void deleteCouponUsage(UUID couponUsageId);
    CouponUsageResponse getCouponUsage(UUID couponUsageId);
    CouponUsageEntity findById(UUID couponUsageId);
}
