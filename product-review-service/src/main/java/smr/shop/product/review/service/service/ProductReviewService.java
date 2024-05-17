package smr.shop.product.review.service.service;

import smr.shop.product.review.service.dto.request.CreateProductReviewRequest;
import smr.shop.product.review.service.dto.request.UpdateProductReviewRequest;

import java.util.UUID;

public interface ProductReviewService {
    void createProductReview(CreateProductReviewRequest request);

    void deleteProductReview(UUID id);

    void updateProductReviewRequest(CreateProductReviewRequest request, UpdateProductReviewRequest updateRequest);
}
