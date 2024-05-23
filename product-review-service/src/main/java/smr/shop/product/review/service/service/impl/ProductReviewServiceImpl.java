package smr.shop.product.review.service.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import smr.shop.libs.common.constant.ServiceConstants;
import smr.shop.libs.common.dto.message.ProductReviewMessageModel;
import smr.shop.libs.common.helper.UserHelper;
import smr.shop.product.review.service.dto.request.CreateProductReviewRequest;
import smr.shop.product.review.service.dto.request.UpdateProductReviewRequest;
import smr.shop.product.review.service.dto.response.ProductReviewResponse;
import smr.shop.product.review.service.exception.ProductReviewServiceException;
import smr.shop.product.review.service.mapper.ProductReviewServiceMapper;
import smr.shop.product.review.service.message.publisher.ProductRatingChangeMessagePublisher;
import smr.shop.product.review.service.message.publisher.ProductReviewUpdateMessagePublisher;
import smr.shop.product.review.service.model.ProductReviewEntity;
import smr.shop.product.review.service.repository.ProductReviewRepository;
import smr.shop.product.review.service.service.ProductReviewService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ProductReviewServiceImpl implements ProductReviewService {

    private final ProductReviewServiceMapper productReviewServiceMapper;
    private final ProductReviewRepository productReviewRepository;

    // message broker
    private final ProductRatingChangeMessagePublisher productRatingChangeMessagePublisher;
    private final ProductReviewUpdateMessagePublisher productReviewUpdateMessagePublisher;

    public ProductReviewServiceImpl(ProductReviewServiceMapper productReviewServiceMapper,
                                    ProductReviewRepository productReviewRepository,
                                    ProductRatingChangeMessagePublisher productRatingChangeMessagePublisher,
                                    ProductReviewUpdateMessagePublisher productReviewUpdateMessagePublisher) {
        this.productReviewServiceMapper = productReviewServiceMapper;
        this.productReviewRepository = productReviewRepository;
        this.productRatingChangeMessagePublisher = productRatingChangeMessagePublisher;
        this.productReviewUpdateMessagePublisher = productReviewUpdateMessagePublisher;
    }

//    ----------------------------------- Create or Add -----------------------------------

    @Override
    public List<ProductReviewResponse> getProductReviews(Long productId, Integer page) {
        Pageable pageable = PageRequest.of(page, ServiceConstants.pageSize);
        List<ProductReviewEntity> productReviewEntityList = productReviewRepository.findAllByproductId(productId, pageable).stream().toList();
        return productReviewEntityList.stream().map(productReviewServiceMapper::productReviewEntityToProductReviewResponse).toList();

    }


//    ----------------------------------- Create or Add -----------------------------------

    @Override
    @Transactional
    public void createProductReview(Long productId, CreateProductReviewRequest request) {
        UUID userId = UserHelper.getUserId();
        boolean existsByProductId = productReviewRepository.existsByProductIdAndUserId(productId, userId);
        if (existsByProductId) {
            throw new ProductReviewServiceException("User has already reviewed this product", HttpStatus.BAD_REQUEST);
        }
        ProductReviewEntity productReviewEntity = productReviewServiceMapper.createProductReviewRequestToProductReviewEntity(request);
        productReviewEntity.setId(UUID.randomUUID());
        productReviewEntity.setUserId(userId);
        productReviewEntity.setProductId(productId);
        productReviewEntity.setCreatedAt(ZonedDateTime.of(LocalDateTime.now(), ZoneId.of(ServiceConstants.UTC)));
        productReviewEntity.setUpdatedAt(ZonedDateTime.of(LocalDateTime.now(), ZoneId.of(ServiceConstants.UTC)));
        ProductReviewEntity reviewEntity = productReviewRepository.save(productReviewEntity);

        ProductReviewMessageModel productReviewMessageModel = ProductReviewMessageModel.builder().productId(reviewEntity.getProductId()).build();
        productReviewUpdateMessagePublisher.publish(productReviewMessageModel);
    }


//    ----------------------------------- Update -----------------------------------

    @Override
    @Transactional
    public void updateProductReviewRequest(UUID reviewId, UpdateProductReviewRequest request) {
        ProductReviewEntity productReviewEntity = findById(reviewId);
        if (!productReviewEntity.getUserId().equals(UserHelper.getUserId())) {
            throw new ProductReviewServiceException("You dont have a permission to update this review with id: " + reviewId, HttpStatus.BAD_REQUEST);
        }
        productReviewServiceMapper.updateProductReviewRequestToUpdateProductReviewEntity(request, productReviewEntity);
        productReviewEntity.setUpdatedAt(ZonedDateTime.of(LocalDateTime.now(), ZoneId.of(ServiceConstants.UTC)));
        productReviewRepository.save(productReviewEntity);
        productReviewServiceMapper.productReviewEntityToProductReviewResponse(productReviewEntity);
    }

    @Override
    public void calculateRating(ProductReviewMessageModel message) {
        Long productId = message.getProductId();
        List<ProductReviewEntity> list = productReviewRepository.findAllByproductId(productId).stream().toList();
        int sum = list.stream().mapToInt(ProductReviewEntity::getRating).sum();
        float totalRating = (float) (sum / list.size());
        ProductReviewMessageModel productReviewMessageModel = ProductReviewMessageModel.builder()
                .productId(message.getProductId())
                .rating(totalRating)
                .build();

        productRatingChangeMessagePublisher.publish(productReviewMessageModel);

    }


//    ----------------------------------- Extra -----------------------------------

    public ProductReviewEntity findById(UUID id) {
        return productReviewRepository.findById(id)
                .orElseThrow(() -> new ProductReviewServiceException("Product Review Not Found With Id: " + id, HttpStatus.NOT_FOUND));
    }
}
