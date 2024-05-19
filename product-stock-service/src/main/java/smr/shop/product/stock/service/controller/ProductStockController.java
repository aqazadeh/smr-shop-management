package smr.shop.product.stock.service.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smr.shop.libs.common.dto.response.EmptyResponse;
import smr.shop.product.stock.service.dto.request.CreateProductStockRequest;
import smr.shop.product.stock.service.dto.request.UpdateProductStockRequest;
import smr.shop.product.stock.service.dto.response.ProductStockResponse;
import smr.shop.product.stock.service.service.ProductStockService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/1.0/products")
public class ProductStockController {
    private final ProductStockService productStockService;

    public ProductStockController(ProductStockService productStockService) {
        this.productStockService = productStockService;
    }

//    ----------------------------------- Post -----------------------------------

    @PostMapping("/stocks")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<EmptyResponse> create(@RequestBody @Valid CreateProductStockRequest request) {
        productStockService.create(request);
        EmptyResponse response = EmptyResponse.builder().message("Product stock created").build();
        return ResponseEntity.ok(response);
    }

//    ----------------------------------- Put or Patch -----------------------------------

    @PutMapping("/stocks/{stockId}")
    @ResponseStatus(HttpStatus.OK)
    public void updateById(@PathVariable UUID stockId,
                           @RequestBody @Valid UpdateProductStockRequest request) {
        productStockService.update(stockId, request);
    }

//    ----------------------------------- Delete -----------------------------------

    @DeleteMapping("/stocks/{stockId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteById(@PathVariable UUID stockId) {
        productStockService.delete(stockId);
    }

//    ----------------------------------- Get -----------------------------------

    @GetMapping("/stocks/{stockId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ProductStockResponse> getById(@PathVariable UUID stockId) {
        ProductStockResponse productStockResponse = productStockService.getById(stockId);
        return ResponseEntity.ok(productStockResponse);
    }

    @GetMapping("/{productId}/stocks")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductStockResponse> getByProductId(@PathVariable Long productId) {
        return productStockService.getByProductId(productId);
    }

}