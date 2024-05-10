package smr.shop.product.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import smr.shop.product.service.model.ProductEntity;
import smr.shop.product.service.model.valueobject.ProductStatus;

import java.util.List;

/**
 * Author: Ali Gadashov
 * Version: v1.0
 * Date: 5/11/2024
 * Time: 12:41 AM
 */

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
    List<ProductEntity> findByBrandId(Long brandId);

    @Transactional
    @Modifying
    @Query("update ProductEntity p set p.status = ?1 where p.brandId = ?2")
    void updateStatusByBrandId(ProductStatus status, Long brandId);


    @Transactional
    @Modifying
    @Query("update ProductEntity p set p.status = ?1 where p.categoryId = ?2")
    void updateStatusByCategoryId(ProductStatus productStatus, Long categoryId);
}
