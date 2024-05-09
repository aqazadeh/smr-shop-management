package smr.shop.brand.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smr.shop.brand.service.model.BrandEntity;

public interface BrandRepository extends JpaRepository<BrandEntity, Long> {
}
