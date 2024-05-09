package smr.shop.coupon.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smr.shop.coupon.service.model.CouponUsageEntity;
import smr.shop.coupon.service.model.UserCouponEntity;

import java.util.Optional;
import java.util.UUID;

/**
 * Author: Ali Gadashov
 * Version: v1.0
 * Date: 5/9/2024
 * Time: 4:45 PM
 */

public interface UserCouponRepository extends JpaRepository<UserCouponEntity, UUID> {

    Optional<UserCouponEntity> findUserCouponByCouponId(UUID couponId);
}