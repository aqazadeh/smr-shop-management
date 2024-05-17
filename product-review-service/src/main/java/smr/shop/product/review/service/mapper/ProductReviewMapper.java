package smr.shop.product.review.service.mapper;

import org.springframework.stereotype.Component;
import smr.shop.product.review.service.dto.request.CreateProductReviewRequest;
import smr.shop.product.review.service.dto.response.ProductReviewResponse;
import smr.shop.product.review.service.model.ProductReview;

@Component
public class ProductReviewMapper {
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
}
