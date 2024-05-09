package smr.shop.delivery.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smr.shop.delivery.service.model.Delivery;

public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
}
