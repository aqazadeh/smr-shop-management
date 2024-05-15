package smr.shop.coupon.service.service;

import smr.shop.coupon.service.dto.response.CouponUsageResponse;
import smr.shop.libs.common.dto.message.UseCouponMessageModel;
import smr.shop.libs.grpc.coupon.CouponGrpcResponse;

import java.util.UUID;

/**
 * Author: Ali Gadashov
 * Version: v1.0
 * Date: 5/9/2024
 * Time: 4:49 PM
 */

public interface CouponUsageService {


    void createCouponUsage(UseCouponMessageModel useCouponMessageModel);

    boolean getCouponUsage(UUID couponId, Long userId);
}
