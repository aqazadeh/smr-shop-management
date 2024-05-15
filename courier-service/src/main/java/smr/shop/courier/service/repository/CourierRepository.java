package smr.shop.courier.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smr.shop.courier.service.model.CourierEntity;

public interface CourierRepository extends JpaRepository<CourierEntity, Long> {
}
