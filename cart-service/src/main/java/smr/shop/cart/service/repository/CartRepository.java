package smr.shop.cart.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import smr.shop.cart.service.model.CartEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Author: Ali Gadashov
 * Version: v1.0
 * Date: 5/10/2024
 * Time: 1:26 PM
 */

public interface CartRepository extends JpaRepository<CartEntity, UUID> {

    Optional<CartEntity> findByUserId(UUID userId);


     List<CartEntity> findByCoupon(String code);



    @Transactional
    @Modifying
    @Query("update CartEntity c set c.coupon = null where c.coupon = ?1")
    void removeCouponInCart(String coupon);
}
