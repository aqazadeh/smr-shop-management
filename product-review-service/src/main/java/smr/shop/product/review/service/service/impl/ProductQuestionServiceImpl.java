package smr.shop.product.review.service.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import smr.shop.libs.common.constant.ServiceConstants;
import smr.shop.libs.common.helper.UserHelper;
import smr.shop.libs.grpc.client.ProductGrpcClient;
import smr.shop.libs.grpc.product.ProductGrpcResponse;
import smr.shop.product.review.service.dto.request.CreateProductQuestionRequest;
import smr.shop.product.review.service.dto.request.UpdateProductQuestionRequest;
import smr.shop.product.review.service.dto.response.ProductQuestionResponse;
import smr.shop.product.review.service.exception.ProductReviewServiceException;
import smr.shop.product.review.service.mapper.ProductReviewServiceMapper;
import smr.shop.product.review.service.model.ProductQuestionEntity;
import smr.shop.product.review.service.model.valueobject.ProductQuestionStatus;
import smr.shop.product.review.service.repository.ProductQuestionRepository;
import smr.shop.product.review.service.service.ProductQuestionService;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ProductQuestionServiceImpl implements ProductQuestionService {

    // repository
    private final ProductQuestionRepository productQuestionRepository;

    // service mapper
    private final ProductReviewServiceMapper productReviewServiceMapper;

    // grpc
    private final ProductGrpcClient productGrpcClient;

    public ProductQuestionServiceImpl(ProductQuestionRepository productQuestionRepository,
                                      ProductReviewServiceMapper productReviewServiceMapper,
                                      ProductGrpcClient productGrpcClient) {
        this.productQuestionRepository = productQuestionRepository;
        this.productReviewServiceMapper = productReviewServiceMapper;
        this.productGrpcClient = productGrpcClient;
    }


//    ----------------------------------- Create or Add -----------------------------------

    @Override
    @Transactional
    public void createProductQuestion(Long productId, CreateProductQuestionRequest request) {
        ProductGrpcResponse productGrpcResponse = productGrpcClient.getProductByProductId(productId);
        ProductQuestionEntity productQuestionEntity = productReviewServiceMapper.createProductQuestionRequestToProductQuestionEntity(request);
        if (request.getQuestionId() != null) {
            ProductQuestionEntity parentQuestion = findById(request.getQuestionId());
            productQuestionEntity.setQuestion(parentQuestion.getQuestion());

        }
        productQuestionEntity.setId(UUID.randomUUID());
        productQuestionEntity.setProductId(productGrpcResponse.getId());
        productQuestionEntity.setUserId(UserHelper.getUserId());
        productQuestionEntity.setCreatedAt(ZonedDateTime.now(ServiceConstants.ZONE_ID));
        productQuestionEntity.setUpdatedAt(ZonedDateTime.now(ServiceConstants.ZONE_ID));
        productQuestionEntity = productQuestionRepository.save(productQuestionEntity);
        productReviewServiceMapper.productQuestionEntityToProductQuestionResponse(productQuestionEntity);
    }

    //    ----------------------------------- Get -----------------------------------

    @Override
    public List<ProductQuestionResponse> getProductQuestions(Long productId, Integer page) {
        Pageable pageable = PageRequest.of(page, ServiceConstants.pageSize);
        List<ProductQuestionEntity> productQuesntionEntityList = productQuestionRepository.findAllByProductId(productId, pageable).stream().toList();
        return productQuesntionEntityList.stream().map(productReviewServiceMapper::productQuestionEntityToProductQuestionResponse).toList();

    }

//    ----------------------------------- Update -----------------------------------

    @Override
    @Transactional
    public void updateProductQuestion(UUID id, UpdateProductQuestionRequest request) {
        ProductQuestionEntity question = findById(id);
        validateQuestion(id, question);
        productReviewServiceMapper.updateProductQuestionRequestToUpdateProductQuestionEntity(request, question);
        question.setUpdatedAt(ZonedDateTime.of(LocalDateTime.now(), ZoneId.of(ServiceConstants.UTC)));
        productQuestionRepository.save(question);
        productReviewServiceMapper.productQuestionEntityToProductQuestionResponse(question);
    }

//    ----------------------------------- Delete -----------------------------------

    @Override
    @Transactional
    public void deleteProductQuestion(UUID id) {
        ProductQuestionEntity question = findById(id);
        validateQuestion(id, question);
        question.setStatus(ProductQuestionStatus.DELETED);
        question.setUpdatedAt(ZonedDateTime.of(LocalDateTime.now(), ZoneId.of(ServiceConstants.UTC)));
        productQuestionRepository.save(question);
    }

//    ----------------------------------- Helper -----------------------------------

    public ProductQuestionEntity findById(UUID id) {
        return productQuestionRepository.findById(id)
                .orElseThrow(() -> new ProductReviewServiceException("Product Question Not Found With Id: " + id, HttpStatus.NOT_FOUND));
    }

    private static void validateQuestion(UUID id, ProductQuestionEntity question) {
        if (!question.getUserId().equals(UserHelper.getUserId()) ) {
            throw new ProductReviewServiceException("You dont have a permission to update question with id: " + id, HttpStatus.FORBIDDEN);
        }
    }
}
