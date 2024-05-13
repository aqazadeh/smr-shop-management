package smr.shop.shop.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smr.shop.shop.service.model.Shop;

public interface ShopRepository extends JpaRepository<Shop, Long> {
}
