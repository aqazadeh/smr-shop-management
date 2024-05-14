package smr.shop.product.review.service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smr.shop.product.review.service.dto.request.CreateProductReviewRequest;
import smr.shop.product.review.service.dto.response.ProductReviewResponse;
import smr.shop.product.review.service.service.ProductReviewService;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/product-review")
public class ProductReviewController {

    private final ProductReviewService productReviewService;

    public ProductReviewController(ProductReviewService productReviewService) {
        this.productReviewService = productReviewService;
    }

    @PostMapping
    public ResponseEntity<ProductReviewResponse> createProductReview(@RequestBody CreateProductReviewRequest request) {
        ProductReviewResponse productReviewResponse = productReviewService.createProductReview(request);
        return ResponseEntity.ok(productReviewResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProductReview(@PathVariable UUID id) {
        productReviewService.deleteProductReview(id);
        return ResponseEntity.noContent().build();
    }
}
