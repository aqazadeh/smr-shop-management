package smr.shop.product.review.service.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import smr.shop.libs.common.constant.ServiceConstants;
import smr.shop.product.review.service.dto.request.CreateProductQuestionRequest;
import smr.shop.product.review.service.dto.request.UpdateProductQuestionRequest;
import smr.shop.product.review.service.exception.ProductQuestionServiceException;
import smr.shop.product.review.service.mapper.ProductQuestionServiceMapper;
import smr.shop.product.review.service.model.ProductQuestion;
import smr.shop.product.review.service.repository.ProductQuestionRepository;
import smr.shop.product.review.service.repository.ProductReviewRepository;
import smr.shop.product.review.service.service.ProductQuestionService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;

@Service
public class ProductQuestionServiceImpl implements ProductQuestionService {

    private final ProductQuestionRepository productQuestionRepository;
    private final ProductQuestionServiceMapper productQuestionServiceMapper;

    public ProductQuestionServiceImpl(ProductQuestionRepository productQuestionRepository, ProductQuestionServiceMapper productQuestionServiceMapper, ProductReviewRepository productReviewRepository) {
        this.productQuestionRepository = productQuestionRepository;
        this.productQuestionServiceMapper = productQuestionServiceMapper;
    }

    @Override
    @Transactional
    public void createProductQuestion(CreateProductQuestionRequest request) {
        ProductQuestion productQuestion = productQuestionServiceMapper.toProductQuestion(request);
        productQuestion.setCreatedAt(ZonedDateTime.of(LocalDateTime.now(), ZoneId.of(ServiceConstants.UTC)));
        productQuestion.setUpdatedAt(ZonedDateTime.of(LocalDateTime.now(), ZoneId.of(ServiceConstants.UTC)));
        productQuestion = productQuestionRepository.save(productQuestion);
        productQuestionServiceMapper.toProductQuestionResponse(productQuestion);
    }

    @Override
    @Transactional
    public void deleteProductQuestion(UUID id) {
        ProductQuestion question = findById(id);
        productQuestionRepository.delete(question);
    }

    @Override
    public void updateProductReview(UUID id, UpdateProductQuestionRequest request) {
        ProductQuestion question = findById(id);
        productQuestionServiceMapper.toUpdateProductQuestion(request, question);
        productQuestionRepository.save(question);
        productQuestionServiceMapper.toProductQuestionResponse(question);
    }

    public ProductQuestion findById(UUID id) {
        return productQuestionRepository.findById(id)
                .orElseThrow(() -> new ProductQuestionServiceException("Product Question Not Found With Id: " + id, HttpStatus.NOT_FOUND));
    }
}
