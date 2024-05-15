package smr.shop.product.review.service.service;

import smr.shop.product.review.service.dto.request.CreateProductQuestionRequest;
import smr.shop.product.review.service.dto.request.CreateProductReviewRequest;
import smr.shop.product.review.service.dto.response.ProductQuestionResponse;
import smr.shop.product.review.service.dto.response.ProductReviewResponse;

import java.util.UUID;

public interface ProductReviewService {
    ProductReviewResponse createProductReview(CreateProductReviewRequest request);

    void deleteProductReview(UUID id);
}
