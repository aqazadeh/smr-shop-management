package smr.shop.product.review.service.mapper;

import org.springframework.stereotype.Component;
import smr.shop.product.review.service.dto.request.CreateProductQuestionRequest;
import smr.shop.product.review.service.dto.request.UpdateProductQuestionRequest;
import smr.shop.product.review.service.dto.response.ProductQuestionResponse;
import smr.shop.product.review.service.model.ProductQuestion;

@Component
public class ProductQuestionServiceMapper {
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
