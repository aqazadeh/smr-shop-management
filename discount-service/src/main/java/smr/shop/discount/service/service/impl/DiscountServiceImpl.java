package smr.shop.discount.service.service.impl;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import smr.discount.libs.grpc.product.discount.DiscountGrpcResponse;
import smr.shop.discount.service.dto.request.DiscountCreateRequest;
import smr.shop.discount.service.dto.response.DiscountResponse;
import smr.shop.discount.service.exception.DiscountServiceException;
import smr.shop.discount.service.mapper.DiscountServiceMapper;
import smr.shop.discount.service.model.DiscountEntity;
import smr.shop.discount.service.repository.DiscountRepository;
import smr.shop.discount.service.service.DiscountService;
import smr.shop.libs.common.constant.ServiceConstants;
import smr.shop.libs.common.helper.UserHelper;
import smr.shop.libs.grpc.client.ProductGrpcClient;
import smr.shop.libs.grpc.client.ShopGrpcClient;
import smr.shop.libs.grpc.object.DiscountGrpcId;
import smr.shop.libs.grpc.product.ProductGrpcResponse;
import smr.shop.libs.grpc.product.shop.ShopGrpcResponse;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class DiscountServiceImpl implements DiscountService {
    private final DiscountRepository discountRepository;
    private final DiscountServiceMapper discountServiceMapper;

    // rpc
    private final ShopGrpcClient shopGrpcClient;
    private final ProductGrpcClient productGrpcClient;

    public DiscountServiceImpl(DiscountRepository discountRepository,
                               DiscountServiceMapper discountServiceMapper,
                               ShopGrpcClient shopGrpcClient,
                               ProductGrpcClient productGrpcClient) {
        this.discountRepository = discountRepository;
        this.discountServiceMapper = discountServiceMapper;
        this.shopGrpcClient = shopGrpcClient;
        this.productGrpcClient = productGrpcClient;
    }


//    ----------------------------------- Create or Add -----------------------------------

    @Override
    public void createDiscount(Long productId, DiscountCreateRequest request) {
        UUID userId = UserHelper.getUserId();
        ShopGrpcResponse shopGrpcResponse = shopGrpcClient.getShopByUserId(userId.toString());
        ProductGrpcResponse productGrpcResponse = productGrpcClient.getProductByProductId(productId);
        if (productGrpcResponse.getShopId() != shopGrpcResponse.getId()) {
            throw new DiscountServiceException("you dont have a permission to add product discount with id: " + productId, HttpStatus.FORBIDDEN);
        }
        DiscountEntity discountEntity = discountServiceMapper.discountCreateRequestToDiscountEntity(request);
        discountEntity.setId(UUID.randomUUID());
        discountEntity.setShopId(shopGrpcResponse.getId());
        discountEntity.setCreatedAt(ZonedDateTime.now(ServiceConstants.ZONE_ID));
        discountEntity.setUpdatedAt(ZonedDateTime.now(ServiceConstants.ZONE_ID));
        discountRepository.save(discountEntity);
    }


//    ----------------------------------- Delete -----------------------------------

    @Override
    public void deleteDiscount(UUID discountId) {
        UUID userId = UserHelper.getUserId();
        ShopGrpcResponse shopGrpcResponse = shopGrpcClient.getShopByUserId(userId.toString());
        DiscountEntity discountEntity = findById(discountId);
        if (discountEntity.getShopId() != shopGrpcResponse.getId()) {
            throw new DiscountServiceException("you dont have a permission to remove discount with id: " + discountId, HttpStatus.FORBIDDEN);
        }
        discountEntity.setIsDeleted(true);
        discountEntity.setUpdatedAt(ZonedDateTime.now(ServiceConstants.ZONE_ID));
        discountRepository.save(discountEntity);
    }

//    ----------------------------------- Get -----------------------------------


    @Override
    public List<DiscountResponse> getShopDiscounts(Long shopId, Integer page) {
        Pageable pageable = PageRequest.of(page, ServiceConstants.pageSize);
        return discountRepository.findAllByShopIdAndIsDeletedFalse(shopId, pageable).stream()
                .map(discountServiceMapper::discountEntityToDiscountResponse).toList();
    }

    @Override
    public DiscountResponse getDiscountById(UUID discountId) {
        DiscountEntity discountEntity = findById(discountId);
        validateDiscount(discountEntity);
        return discountServiceMapper.discountEntityToDiscountResponse(discountEntity);
    }

//    ----------------------------------- Extra -----------------------------------

    @Override
    public DiscountEntity findById(UUID discountId) {
        return discountRepository.findById(discountId)
                .orElseThrow(() -> new DiscountServiceException("Discount Not found With id : " + discountId, HttpStatus.NOT_FOUND));
    }

    @Override
    public DiscountGrpcResponse getDiscountById(DiscountGrpcId request) {
        DiscountEntity discountEntity = findById(UUID.fromString(request.getId()));
        validateDiscountExpiration(discountEntity);
        return discountServiceMapper.discountEntityToDiscountGrpcResponse(discountEntity);
    }

    private void validateDiscount(DiscountEntity discountEntity) {
        if (discountEntity.getIsDeleted().equals(Boolean.TRUE)) {
            throw new DiscountServiceException("Discount Not found With id : " + discountEntity.getId(), HttpStatus.NOT_FOUND);
        }
    }

    private void validateDiscountExpiration(DiscountEntity discountEntity) {
        if (discountEntity.getCreatedAt().isAfter(ZonedDateTime.now(ServiceConstants.ZONE_ID))) {
            throw new DiscountServiceException("Discount Expired With id : " + discountEntity.getId(), HttpStatus.BAD_REQUEST);
        }
    }
}
