package smr.shop.product.review.service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import smr.shop.product.review.service.model.ProductQuestionEntity;

import java.util.UUID;

public interface ProductQuestionRepository extends JpaRepository<ProductQuestionEntity, UUID> {
    Page<ProductQuestionEntity> findAllByProductId(Long productId, Pageable pageable);
}
