package smr.shop.product.review.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smr.shop.product.review.service.model.ProductReview;

import java.util.Optional;
import java.util.UUID;

public interface ProductReviewRepository extends JpaRepository<ProductReview, UUID> {
    boolean existsByProductId(Long productId, UUID userId);
    Optional<ProductReview> findByUserIdAndProductId(UUID userId, Long productId);
}
