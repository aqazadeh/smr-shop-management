package smr.shop.flash.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smr.shop.flash.service.model.FlashDealEntity;

public interface FlashDealRepository extends JpaRepository<FlashDealEntity, Long> {
}
