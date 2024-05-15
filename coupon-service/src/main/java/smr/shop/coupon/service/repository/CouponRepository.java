package smr.shop.coupon.service.repository;

import com.netflix.appinfo.ApplicationInfoManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import smr.shop.coupon.service.model.CouponEntity;

import java.util.Optional;
import java.util.UUID;

/**
 * Author: Ali Gadashov
 * Version: v1.0
 * Date: 5/9/2024
 * Time: 3:11 PM
 */

public interface CouponRepository extends JpaRepository<CouponEntity, UUID> {

    Optional<CouponEntity> findByCode(String couponCode);

    Page<CouponEntity> findAllByIsDeletedFalse(Pageable pageable);

    Optional<CouponEntity> findByIdAndIsDeletedFalse(UUID couponId);

    Page<CouponEntity> findAllByShopIdAndIsDeletedFalse(long shopId, Pageable pageable);
}
