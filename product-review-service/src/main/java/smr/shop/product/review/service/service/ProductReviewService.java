package smr.shop.product.review.service.service;

import smr.shop.product.review.service.dto.request.CreateProductReviewRequest;

import java.util.UUID;

public interface ProductReviewService {
    void createProductReview(CreateProductReviewRequest request);

    void deleteProductReview(UUID id);
}
