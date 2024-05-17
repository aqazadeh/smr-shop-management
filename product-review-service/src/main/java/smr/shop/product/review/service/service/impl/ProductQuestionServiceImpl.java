package smr.shop.product.review.service.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import smr.shop.libs.common.constant.ServiceConstants;
import smr.shop.product.review.service.dto.request.CreateProductQuestionRequest;
import smr.shop.product.review.service.dto.response.ProductQuestionResponse;
import smr.shop.product.review.service.exception.ProductQuestionException;
import smr.shop.product.review.service.mapper.ProductQuestionMapper;
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
    private final ProductQuestionMapper productQuestionMapper;

    public ProductQuestionServiceImpl(ProductQuestionRepository productQuestionRepository, ProductQuestionMapper productQuestionMapper, ProductReviewRepository productReviewRepository) {
        this.productQuestionRepository = productQuestionRepository;
        this.productQuestionMapper = productQuestionMapper;
    }

    @Override
    @Transactional
    public void createProductQuestion(CreateProductQuestionRequest request) {
        ProductQuestion productQuestion = productQuestionMapper.toProductQuestion(request);
        productQuestion.setCreatedAt(ZonedDateTime.of(LocalDateTime.now(), ZoneId.of(ServiceConstants.UTC)));
        productQuestion.setUpdatedAt(ZonedDateTime.of(LocalDateTime.now(), ZoneId.of(ServiceConstants.UTC)));
        productQuestion = productQuestionRepository.save(productQuestion);
        productQuestionMapper.toProductQuestionResponse(productQuestion);
    }

    @Override
    @Transactional
    public void deleteProductQuestion(UUID id) {
        ProductQuestion question = findById(id);
        productQuestionRepository.delete(question);
    }

    public ProductQuestion findById(UUID id) {
        return productQuestionRepository.findById(id)
                .orElseThrow(() -> new ProductQuestionException("Product Question Not Found With Id: " + id, HttpStatus.NOT_FOUND));
    }
}
