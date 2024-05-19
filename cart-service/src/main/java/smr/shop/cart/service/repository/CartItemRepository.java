package smr.shop.cart.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smr.shop.cart.service.model.CartItemEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Author: Ali Gadashov
 * Version: v1.0
 * Date: 5/10/2024
 * Time: 1:26 PM
 */

public interface CartItemRepository extends JpaRepository<CartItemEntity, UUID> {

    Optional<CartItemEntity> findByUserIdAndProductIdAndStockId(UUID userId, Long productId, UUID attributeId);

    List<CartItemEntity> findByCartId(UUID cartId);

    void deleteByProductId(Long productId);

    void deleteByStockId(UUID stockId);

    void  deleteAllByCartId(UUID cartId);
}
