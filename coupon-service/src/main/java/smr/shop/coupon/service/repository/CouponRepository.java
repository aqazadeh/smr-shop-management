package smr.shop.coupon.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smr.shop.coupon.service.model.CouponEntity;

import java.util.UUID;

/**
 * Author: Ali Gadashov
 * Version: v1.0
 * Date: 5/9/2024
 * Time: 3:11 PM
 */

public interface CouponRepository extends JpaRepository<CouponEntity, UUID> {
}
