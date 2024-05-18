package smr.shop.product.review.service.mapper;

import org.springframework.stereotype.Component;
import smr.shop.product.review.service.dto.request.CreateProductQuestionRequest;
import smr.shop.product.review.service.dto.request.CreateProductReviewRequest;
import smr.shop.product.review.service.dto.request.UpdateProductQuestionRequest;
import smr.shop.product.review.service.dto.request.UpdateProductReviewRequest;
import smr.shop.product.review.service.dto.response.ProductQuestionResponse;
import smr.shop.product.review.service.dto.response.ProductReviewResponse;
import smr.shop.product.review.service.model.ProductQuestion;
import smr.shop.product.review.service.model.ProductReview;

@Component
public class ProductReviewServiceMapper {
    public ProductReview toProductReview(CreateProductReviewRequest request) {
        ProductReview.ProductReviewBuilder builder = ProductReview.builder();
        builder.productId(request.getProductId());
        builder.rating(request.getRating());
        builder.comment(request.getComment());
        builder.images(request.getImages());
        return builder.build();
    }

    public ProductReviewResponse toProductReviewResponse(ProductReview productReview) {
        return ProductReviewResponse.builder()
                .id(productReview.getId())
                .userId(productReview.getUserId())
                .productId(productReview.getProductId())
                .rating(productReview.getRating())
                .comment(productReview.getComment())
                .images(productReview.getImages())
                .build();
    }

    public ProductReview toUpdateProductReview(UpdateProductReviewRequest request, ProductReview review) {
        if (request.getUserId() != null) review.setUserId(request.getUserId());
        if (request.getProductId() != null) review.setProductId(request.getProductId());
        if (request.getRating() != null) review.setRating(request.getRating());
        if (request.getComment() != null) review.setComment(request.getComment());
        if (request.getImages() != null) review.setImages(request.getImages());
        return review;
    }

    public ProductQuestion toProductQuestion(CreateProductQuestionRequest request) {
        ProductQuestion.ProductQuestionBuilder builder = ProductQuestion.builder();
        builder.userId(request.getUserId());
        builder.productId(request.getProductId());
        builder.question(request.getQuestion());
        builder.questionId(request.getQuestionId());
        return builder.build();
    }

    public ProductQuestionResponse toProductQuestionResponse(ProductQuestion productQuestion) {
        return ProductQuestionResponse.builder()
                .id(productQuestion.getId())
                .userId(productQuestion.getUserId())
                .productId(productQuestion.getProductId())
                .question(productQuestion.getQuestion())
                .questionId(productQuestion.getQuestionId())
                .build();
    }

    public ProductQuestion toUpdateProductQuestion(UpdateProductQuestionRequest request, ProductQuestion productQuestion) {
        if (request.getUserId() != null) productQuestion.setUserId(request.getUserId());
        if (request.getProductId() != null) productQuestion.setProductId(request.getProductId());
        if (request.getQuestion() != null) productQuestion.setQuestion(request.getQuestion());
        if (request.getQuestionId() != null) productQuestion.setQuestionId(request.getQuestionId());
        return productQuestion;
    }
}
