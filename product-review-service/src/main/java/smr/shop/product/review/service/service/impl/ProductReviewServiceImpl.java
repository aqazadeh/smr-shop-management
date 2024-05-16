package smr.shop.product.review.service.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import smr.shop.libs.common.constant.ServiceConstants;
import smr.shop.product.review.service.dto.request.CreateProductReviewRequest;
import smr.shop.product.review.service.exception.ProductReviewException;
import smr.shop.product.review.service.mapper.ProductReviewMapper;
import smr.shop.product.review.service.model.ProductReview;
import smr.shop.product.review.service.repository.ProductReviewRepository;
import smr.shop.product.review.service.service.ProductReviewService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;

@Service
public class ProductReviewServiceImpl implements ProductReviewService {

    private final ProductReviewMapper productReviewMapper;
    private final ProductReviewRepository productReviewRepository;

    public ProductReviewServiceImpl(ProductReviewMapper productReviewMapper, ProductReviewRepository productReviewRepository) {
        this.productReviewMapper = productReviewMapper;
        this.productReviewRepository = productReviewRepository;
    }

    @Override
    @Transactional
    public void createProductReview(CreateProductReviewRequest request) {
        ProductReview productReview = productReviewMapper.toProductReview(request);
        productReview.setCreatedAt(ZonedDateTime.of(LocalDateTime.now(), ZoneId.of(ServiceConstants.UTC)));
        productReview.setUpdatedAt(ZonedDateTime.of(LocalDateTime.now(), ZoneId.of(ServiceConstants.UTC)));
        productReview = productReviewRepository.save(productReview);
        productReviewMapper.toProductReviewResponse(productReview);
    }

    @Override
    @Transactional
    public void deleteProductReview(UUID id) {
        ProductReview productReview = findById(id);
        productReviewRepository.delete(productReview);
    }

    public ProductReview findById(UUID id) {
        return productReviewRepository.findById(id)
                .orElseThrow(() -> new ProductReviewException("Product Review Not Found With Id: " + id, HttpStatus.NOT_FOUND));
    }
}
