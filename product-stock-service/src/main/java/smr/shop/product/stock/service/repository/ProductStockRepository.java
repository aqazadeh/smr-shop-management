package smr.shop.product.stock.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smr.shop.product.stock.service.model.ProductStock;

import java.util.Optional;
import java.util.UUID;

public interface ProductStockRepository extends JpaRepository<ProductStock, UUID> {
    Optional<ProductStock> findByProductId(Long productId);
}