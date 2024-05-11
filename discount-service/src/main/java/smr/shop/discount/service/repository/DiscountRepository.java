package smr.shop.discount.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smr.shop.discount.service.model.DiscountEntity;

public interface DiscountRepository extends JpaRepository<DiscountEntity,Long> {

}
