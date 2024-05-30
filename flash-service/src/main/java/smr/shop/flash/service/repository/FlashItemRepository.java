package smr.shop.flash.service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import smr.shop.flash.service.model.FlashItemEntity;

import java.util.Optional;
import java.util.UUID;

/**
 * Author: Ali Gadashov
 * Version: v1.0
 * Date: 5/9/2024
 * Time: 7:58 PM
 */

public interface FlashItemRepository extends JpaRepository<FlashItemEntity, UUID> {
    Page<FlashItemEntity> findByFlashId(Long flashId, Pageable pageable);

    Optional<FlashItemEntity> findByFlashIdAndProductId(Long flashId, Long productId);

    void deleteByFlashId(Long flashId);

    void deleteByProductId(Long productId);
}
