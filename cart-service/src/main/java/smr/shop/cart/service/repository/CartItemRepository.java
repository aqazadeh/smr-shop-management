package smr.shop.cart.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smr.shop.cart.service.model.CartItemEntity;

import java.util.UUID;

/**
 * Author: Ali Gadashov
 * Version: v1.0
 * Date: 5/10/2024
 * Time: 1:26 PM
 */

public interface CartItemRepository extends JpaRepository<CartItemEntity, UUID> {
    void deleteByProductId(Long productId);

}
