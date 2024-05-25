package smr.shop.discount.service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import smr.shop.discount.service.model.DiscountEntity;

import java.util.UUID;

public interface DiscountRepository extends JpaRepository<DiscountEntity, UUID> {

     Page<DiscountEntity> findAllByShopIdAndIsDeletedFalse(Long shopId, Pageable pageable);
}
