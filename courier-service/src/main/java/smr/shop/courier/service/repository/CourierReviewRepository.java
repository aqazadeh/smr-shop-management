package smr.shop.courier.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smr.shop.courier.service.model.CourierReview;

import java.util.UUID;

public interface CourierReviewRepository extends JpaRepository<CourierReview, UUID> {
}
