package smr.shop.shop.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smr.shop.shop.service.model.ShopEntity;

public interface ShopRepository extends JpaRepository<ShopEntity, Long> {
}
