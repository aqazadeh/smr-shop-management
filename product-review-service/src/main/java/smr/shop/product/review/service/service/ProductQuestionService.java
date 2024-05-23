package smr.shop.product.review.service.service;

import smr.shop.product.review.service.dto.request.CreateProductQuestionRequest;
import smr.shop.product.review.service.dto.request.UpdateProductQuestionRequest;
import smr.shop.product.review.service.dto.response.ProductQuestionResponse;

import java.util.List;
import java.util.UUID;

public interface ProductQuestionService {
    void createProductQuestion(Long productId, CreateProductQuestionRequest request);

    void deleteProductQuestion(UUID id);

    List<ProductQuestionResponse> getProductQuestions(Long productId, Integer page);

    void updateProductQuestion(UUID id, UpdateProductQuestionRequest request);
}
