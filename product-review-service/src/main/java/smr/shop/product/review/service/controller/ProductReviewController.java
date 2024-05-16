package smr.shop.product.review.service.controller;

import jakarta.transaction.Transactional;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smr.shop.libs.common.dto.response.EmptyResponse;
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
    @Transactional
    public ResponseEntity<EmptyResponse> createProductReview(@RequestBody CreateProductReviewRequest request) {
        productReviewService.createProductReview(request);
        EmptyResponse response = EmptyResponse.builder()
                .message("Product Review created successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<EmptyResponse> deleteProductReview(@PathVariable UUID id) {
        productReviewService.deleteProductReview(id);
        EmptyResponse response = EmptyResponse.builder()
                .message("Product Review deleted successfully")
                .build();
        return ResponseEntity.ok(response);
    }
}
