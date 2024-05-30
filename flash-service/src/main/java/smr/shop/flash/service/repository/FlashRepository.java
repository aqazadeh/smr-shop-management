package smr.shop.flash.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smr.shop.flash.service.model.FlashEntity;

public interface FlashRepository extends JpaRepository<FlashEntity, Long> {
}
