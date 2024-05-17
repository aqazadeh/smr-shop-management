package smr.shop.product.review.service.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import smr.shop.libs.common.constant.ServiceConstants;
import smr.shop.product.review.service.dto.request.CreateProductReviewRequest;
import smr.shop.product.review.service.dto.request.UpdateProductReviewRequest;
import smr.shop.product.review.service.exception.ProductReviewServiceException;
import smr.shop.product.review.service.mapper.ProductReviewServiceMapper;
import smr.shop.product.review.service.model.ProductReview;
import smr.shop.product.review.service.repository.ProductReviewRepository;
import smr.shop.product.review.service.service.ProductReviewService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductReviewServiceImpl implements ProductReviewService {

    private final ProductReviewServiceMapper productReviewServiceMapper;
    private final ProductReviewRepository productReviewRepository;

    public ProductReviewServiceImpl(ProductReviewServiceMapper productReviewServiceMapper, ProductReviewRepository productReviewRepository) {
        this.productReviewServiceMapper = productReviewServiceMapper;
        this.productReviewRepository = productReviewRepository;
    }

    @Override
    @Transactional
    public void createProductReview(CreateProductReviewRequest request) {
        boolean productId = productReviewRepository.existsByProductId(request.getProductId(), request.getUserId());
        if (productId) throw new IllegalArgumentException("User has already reviewed this product");
        ProductReview productReview = productReviewServiceMapper.toProductReview(request);
        productReview.setCreatedAt(ZonedDateTime.of(LocalDateTime.now(), ZoneId.of(ServiceConstants.UTC)));
        productReview.setUpdatedAt(ZonedDateTime.of(LocalDateTime.now(), ZoneId.of(ServiceConstants.UTC)));
        productReviewRepository.save(productReview);
    }

    @Override
    @Transactional
    public void deleteProductReview(UUID id) {
        ProductReview productReview = findById(id);
        productReview.setUpdatedAt(ZonedDateTime.of(LocalDateTime.now(), ZoneId.of(ServiceConstants.UTC)));
        productReviewRepository.delete(productReview);
    }

    @Override
    @Transactional
    public void updateProductReviewRequest(CreateProductReviewRequest request, UpdateProductReviewRequest updateRequest) {
        Optional<ProductReview> existingReview = productReviewRepository.findByUserIdAndProductId(request.getUserId(), request.getProductId());
        if (existingReview.isPresent()) {
            ProductReview productReview = existingReview.get();
            productReviewServiceMapper.toUpdateProductReview(updateRequest, productReview);
            productReview.setUpdatedAt(ZonedDateTime.of(LocalDateTime.now(), ZoneId.of(ServiceConstants.UTC)));
            productReviewRepository.save(productReview);
            productReviewServiceMapper.toProductReviewResponse(productReview);
        } else {
            ProductReview productReview = productReviewServiceMapper.toProductReview(request);
            productReview.setCreatedAt(ZonedDateTime.of(LocalDateTime.now(), ZoneId.of(ServiceConstants.UTC)));
            productReview.setCreatedAt(ZonedDateTime.of(LocalDateTime.now(), ZoneId.of(ServiceConstants.UTC)));
            productReview = productReviewRepository.save(productReview);
            productReviewServiceMapper.toProductReviewResponse(productReview);
        }
    }

    public ProductReview findById(UUID id) {
        return productReviewRepository.findById(id)
                .orElseThrow(() -> new ProductReviewServiceException("Product Review Not Found With Id: " + id, HttpStatus.NOT_FOUND));
    }
}
