package smr.shop.product.review.service.service;

import smr.shop.product.review.service.dto.request.CreateProductQuestionRequest;
import smr.shop.product.review.service.dto.response.ProductQuestionResponse;

import java.util.UUID;

public interface ProductQuestionService {
    void createProductQuestion(CreateProductQuestionRequest request);

    void deleteProductQuestion(UUID id);
}
