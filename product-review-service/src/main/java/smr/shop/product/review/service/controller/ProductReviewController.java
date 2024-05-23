package smr.shop.product.review.service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smr.shop.libs.common.dto.response.EmptyResponse;
import smr.shop.product.review.service.dto.request.CreateProductReviewRequest;
import smr.shop.product.review.service.dto.request.UpdateProductReviewRequest;
import smr.shop.product.review.service.dto.response.ProductReviewResponse;
import smr.shop.product.review.service.service.ProductReviewService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/1.0/review")
public class ProductReviewController {

    private final ProductReviewService productReviewService;

    public ProductReviewController(ProductReviewService productReviewService) {
        this.productReviewService = productReviewService;
    }

//    ----------------------------------- Post -----------------------------------

    @GetMapping("/product/{productId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<List<ProductReviewResponse>> getProductReviews(@PathVariable Long productId,
                                                                         @RequestParam(value = "page", defaultValue = "0") Integer page) {
        List<ProductReviewResponse> productReviewResponseList = productReviewService.getProductReviews(productId, page);

        return ResponseEntity.ok(productReviewResponseList);
    }

//    ----------------------------------- Post -----------------------------------

    @PostMapping("/product/{productId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<EmptyResponse> createProductReview(@PathVariable Long productId,
                                                             @RequestBody CreateProductReviewRequest request) {
        productReviewService.createProductReview(productId, request);
        EmptyResponse response = EmptyResponse.builder()
                .message("Product Review created successfully")
                .build();
        return ResponseEntity.ok(response);
    }

//    ----------------------------------- Put or Patch -----------------------------------

    @PutMapping("/{reviewId}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<EmptyResponse> updateProductReview(@PathVariable UUID reviewId, @RequestBody UpdateProductReviewRequest updateRequest) {
        productReviewService.updateProductReviewRequest(reviewId, updateRequest);
        EmptyResponse response = EmptyResponse.builder()
                .message("Product Review updated successfully")
                .build();
        return ResponseEntity.ok(response);
    }
}
