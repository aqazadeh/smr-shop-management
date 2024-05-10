package smr.shop.cart.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smr.shop.cart.service.model.CartEntity;

import java.util.Optional;

/**
 * Author: Ali Gadashov
 * Version: v1.0
 * Date: 5/10/2024
 * Time: 1:26 PM
 */

public interface CartRepository extends JpaRepository<CartEntity, Long> {

    Optional<CartEntity> findCartEntitiesByUserId(Long userId);
}
