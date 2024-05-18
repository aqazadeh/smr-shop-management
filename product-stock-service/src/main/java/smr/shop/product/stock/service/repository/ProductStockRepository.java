package smr.shop.product.stock.service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import smr.shop.product.stock.service.model.ProductStock;

import java.util.List;
import java.util.UUID;

public interface ProductStockRepository extends JpaRepository<ProductStock, UUID> {
    List<ProductStock> findAllByProductId(Long productId);
}