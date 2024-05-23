package smr.shop.product.review.service.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import smr.shop.product.review.service.model.ProductReviewEntity;

import java.util.List;
import java.util.UUID;

public interface ProductReviewRepository extends JpaRepository<ProductReviewEntity, UUID> {
    boolean existsByProductIdAndUserId(Long productId, UUID userId);

    Page<ProductReviewEntity> findAllByproductId(Long productId, Pageable pageable);

    List<ProductReviewEntity> findAllByproductId(Long productId);
}
