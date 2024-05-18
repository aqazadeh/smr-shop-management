package smr.shop.product.stock.service.helper;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import smr.shop.product.stock.service.exception.ProductStockException;
import smr.shop.product.stock.service.model.ProductStock;
import smr.shop.product.stock.service.repository.ProductStockRepository;

import java.util.List;
import java.util.UUID;

@Component
public class ProductStockServiceHelper {
    private final ProductStockRepository productStockRepository;

    public ProductStockServiceHelper(ProductStockRepository productStockRepository) {
        this.productStockRepository = productStockRepository;
    }

    public ProductStock findById(UUID id) {
        return productStockRepository.findById(id).orElseThrow(() ->
                new ProductStockException("Product Stock not found!", HttpStatus.NOT_FOUND));
    }

    public List<ProductStock> getByProductId(Long productId) {
        return productStockRepository.findAllByProductId(productId);
    }
}