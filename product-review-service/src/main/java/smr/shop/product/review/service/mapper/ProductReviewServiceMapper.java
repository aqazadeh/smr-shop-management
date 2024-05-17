package smr.shop.product.review.service.mapper;

import org.springframework.stereotype.Component;
import smr.shop.product.review.service.dto.request.CreateProductReviewRequest;
import smr.shop.product.review.service.dto.request.UpdateProductReviewRequest;
import smr.shop.product.review.service.dto.response.ProductReviewResponse;
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
}
