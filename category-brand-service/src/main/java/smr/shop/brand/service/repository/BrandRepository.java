package smr.shop.brand.service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import smr.shop.brand.service.model.BrandEntity;

import java.util.Optional;

public interface BrandRepository extends JpaRepository<BrandEntity, Long> {
    Page<BrandEntity> findAllByIsDeletedFalse(Pageable pageable);

    Optional<BrandEntity> findByIdAndIsDeletedFalse(Long id);
}
