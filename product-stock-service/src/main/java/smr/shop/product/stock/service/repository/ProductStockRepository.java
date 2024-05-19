package smr.shop.product.stock.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smr.shop.product.stock.service.model.ProductStockEntity;

import java.util.List;
import java.util.UUID;

public interface ProductStockRepository extends JpaRepository<ProductStockEntity, UUID> {
    List<ProductStockEntity> findAllByProductId(Long productId);
}