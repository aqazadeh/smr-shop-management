package smr.shop.category.brand.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smr.shop.category.brand.service.model.CategoryEntity;

import java.util.List;

/**
 * Author: Ali Gadashov
 * Version: v1.0
 * Date: 5/14/2024
 * Time: 4:53 PM
 */

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    List<CategoryEntity> findByParentIdIsNull();

    CategoryEntity findBySlug(String slug);

    List<CategoryEntity> findByParentIdIsNullAndIsDeletedFalse();
}
