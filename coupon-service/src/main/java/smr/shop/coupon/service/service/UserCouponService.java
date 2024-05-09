package smr.shop.coupon.service.service;

import smr.shop.coupon.service.dto.response.UserCouponResponse;
import smr.shop.coupon.service.model.UserCouponEntity;

import java.util.List;
import java.util.UUID;

/**
 * Author: Ali Gadashov
 * Version: v1.0
 * Date: 5/9/2024
 * Time: 4:42 PM
 */

public interface UserCouponService {
    UserCouponResponse createUserCoupon(UUID couponId);
   void deleteUserCoupon(UUID id);
    List<UserCouponResponse> getAllUserCoupons(Integer page);
    UserCouponResponse getUserCoupon(UUID id);
    UserCouponEntity findById(UUID id);
}
