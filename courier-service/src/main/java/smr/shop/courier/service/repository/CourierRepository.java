package smr.shop.courier.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smr.shop.courier.service.model.Courier;

public interface CourierRepository extends JpaRepository<Courier, Long> {
}
