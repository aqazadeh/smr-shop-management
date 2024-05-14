package smr.shop.product.review.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smr.shop.product.review.service.model.ProductQuestion;

import java.util.UUID;

public interface ProductQuestionRepository extends JpaRepository<ProductQuestion, UUID> {
}
