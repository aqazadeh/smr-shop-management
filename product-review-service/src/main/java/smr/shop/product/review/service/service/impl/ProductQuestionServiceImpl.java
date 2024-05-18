package smr.shop.product.review.service.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import smr.shop.libs.common.constant.ServiceConstants;
import smr.shop.product.review.service.dto.request.CreateProductQuestionRequest;
import smr.shop.product.review.service.dto.request.UpdateProductQuestionRequest;
import smr.shop.product.review.service.exception.ProductQuestionServiceException;
import smr.shop.product.review.service.mapper.ProductReviewServiceMapper;
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
    private final ProductReviewServiceMapper productReviewServiceMapper;

    public ProductQuestionServiceImpl(ProductQuestionRepository productQuestionRepository, ProductReviewServiceMapper productReviewServiceMapper, ProductReviewRepository productReviewRepository) {
        this.productQuestionRepository = productQuestionRepository;
        this.productReviewServiceMapper = productReviewServiceMapper;
    }

//    ----------------------------------- Create or Add -----------------------------------

    @Override
    @Transactional
    public void createProductQuestion(CreateProductQuestionRequest request) {
        ProductQuestion productQuestion = productReviewServiceMapper.toProductQuestion(request);
        productQuestion.setCreatedAt(ZonedDateTime.of(LocalDateTime.now(), ZoneId.of(ServiceConstants.UTC)));
        productQuestion.setUpdatedAt(ZonedDateTime.of(LocalDateTime.now(), ZoneId.of(ServiceConstants.UTC)));
        productQuestion = productQuestionRepository.save(productQuestion);
        productReviewServiceMapper.toProductQuestionResponse(productQuestion);
    }

//    ----------------------------------- Update -----------------------------------

    @Override
    public void updateProductReview(UUID id, UpdateProductQuestionRequest request) {
        ProductQuestion question = findById(id);
        productReviewServiceMapper.toUpdateProductQuestion(request, question);
        question.setUpdatedAt(ZonedDateTime.of(LocalDateTime.now(), ZoneId.of(ServiceConstants.UTC)));
        productQuestionRepository.save(question);
        productReviewServiceMapper.toProductQuestionResponse(question);
    }

//    ----------------------------------- Delete -----------------------------------

    @Override
    @Transactional
    public void deleteProductQuestion(UUID id) {
        ProductQuestion question = findById(id);
        question.setUpdatedAt(ZonedDateTime.of(LocalDateTime.now(), ZoneId.of(ServiceConstants.UTC)));
        productQuestionRepository.delete(question);
    }

//    ----------------------------------- Delete -----------------------------------

    public ProductQuestion findById(UUID id) {
        return productQuestionRepository.findById(id)
                .orElseThrow(() -> new ProductQuestionServiceException("Product Question Not Found With Id: " + id, HttpStatus.NOT_FOUND));
    }
}
