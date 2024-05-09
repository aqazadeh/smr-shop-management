package smr.shop.wishlist.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smr.shop.wishlist.service.model.WishlistEntity;

public interface WishlistRepository extends JpaRepository<WishlistEntity, Long> {
}
