package smr.shop.shop.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smr.shop.shop.service.model.ShopEntity;

import java.util.UUID;

public interface ShopRepository extends JpaRepository<ShopEntity, Long> {
    ShopEntity findByUserId(UUID userId);
}
