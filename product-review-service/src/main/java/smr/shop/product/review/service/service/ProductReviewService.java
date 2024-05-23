package smr.shop.product.review.service.service;

import smr.shop.libs.common.dto.message.ProductReviewMessageModel;
import smr.shop.product.review.service.dto.request.CreateProductReviewRequest;
import smr.shop.product.review.service.dto.request.UpdateProductReviewRequest;
import smr.shop.product.review.service.dto.response.ProductReviewResponse;

import java.util.List;
import java.util.UUID;

public interface ProductReviewService {
    List<ProductReviewResponse> getProductReviews(Long productId, Integer page);

    void createProductReview(Long productId, CreateProductReviewRequest request);

    void updateProductReviewRequest(UUID reviewId, UpdateProductReviewRequest updateRequest);

    void calculateRating(ProductReviewMessageModel message);
}
