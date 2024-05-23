package smr.shop.product.review.service.mapper;

import org.springframework.stereotype.Component;
import smr.shop.product.review.service.dto.request.CreateProductQuestionRequest;
import smr.shop.product.review.service.dto.request.CreateProductReviewRequest;
import smr.shop.product.review.service.dto.request.UpdateProductQuestionRequest;
import smr.shop.product.review.service.dto.request.UpdateProductReviewRequest;
import smr.shop.product.review.service.dto.response.ProductQuestionResponse;
import smr.shop.product.review.service.dto.response.ProductReviewResponse;
import smr.shop.product.review.service.model.ProductQuestionEntity;
import smr.shop.product.review.service.model.ProductReviewEntity;

@Component
public class ProductReviewServiceMapper {
    public ProductReviewEntity createProductReviewRequestToProductReviewEntity(CreateProductReviewRequest request) {
        return ProductReviewEntity.builder()
                .rating(request.getRating())
                .comment(request.getComment())
                .build();
    }

    public ProductReviewResponse productReviewEntityToProductReviewResponse(ProductReviewEntity productReviewEntity) {
        return ProductReviewResponse.builder()
                .id(productReviewEntity.getId())
                .userId(productReviewEntity.getUserId())
                .productId(productReviewEntity.getProductId())
                .rating(productReviewEntity.getRating())
                .comment(productReviewEntity.getComment())
                .images(productReviewEntity.getImages())
                .build();
    }

    public ProductReviewEntity updateProductReviewRequestToUpdateProductReviewEntity(UpdateProductReviewRequest request, ProductReviewEntity review) {
        if (request.getComment() != null) review.setComment(request.getComment());
        return review;
    }

    public ProductQuestionEntity createProductQuestionRequestToProductQuestionEntity(CreateProductQuestionRequest request) {
        return ProductQuestionEntity.builder()
                .question(request.getQuestion())
                .build();
    }

    public ProductQuestionResponse productQuestionEntityToProductQuestionResponse(ProductQuestionEntity productQuestionEntity) {
        return ProductQuestionResponse.builder()
                .id(productQuestionEntity.getId())
                .userId(productQuestionEntity.getUserId())
                .productId(productQuestionEntity.getProductId())
                .question(productQuestionEntity.getQuestion())
                .questionId(productQuestionEntity.getQuestionId())
                .build();
    }

    public ProductQuestionEntity updateProductQuestionRequestToUpdateProductQuestionEntity(UpdateProductQuestionRequest request, ProductQuestionEntity productQuestionEntity) {
        if (request.getQuestion() != null) productQuestionEntity.setQuestion(request.getQuestion());
        return productQuestionEntity;
    }
}
