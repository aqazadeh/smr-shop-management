package smr.shop.product.review.service.mapper;

import org.springframework.stereotype.Component;
import smr.shop.product.review.service.dto.request.CreateProductQuestionRequest;
import smr.shop.product.review.service.dto.response.ProductQuestionResponse;
import smr.shop.product.review.service.model.ProductQuestion;

@Component
public class ProductQuestionMapper {
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
}
