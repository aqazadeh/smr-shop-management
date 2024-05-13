package smr.shop.wishlist.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smr.shop.wishlist.service.model.WishlistEntity;

import java.util.Optional;

public interface WishlistRepository extends JpaRepository<WishlistEntity, Long> {
    void deleteByUserIdAndProductId(Long userId, Long productId);
    Optional<WishlistEntity> findByUserIdAndProductId(Long userId, Long productId);

    void deleteByProductId(Long productId);
}
