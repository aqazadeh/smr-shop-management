package smr.shop.coupon.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smr.shop.coupon.service.model.CouponUsageEntity;

import java.util.Optional;
import java.util.UUID;

/**
 * Author: Ali Gadashov
 * Version: v1.0
 * Date: 5/9/2024
 * Time: 4:46 PM
 */

public interface CouponUsageRepository extends JpaRepository<CouponUsageEntity, UUID> {

    Optional<CouponUsageEntity> findCouponUsageByCouponId(UUID couponId);


    Optional<CouponUsageEntity> findByCouponIdAndUserId(UUID couponId, UUID userId);
}

