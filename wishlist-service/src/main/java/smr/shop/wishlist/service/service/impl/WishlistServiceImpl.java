package smr.shop.wishlist.service.service.impl;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import smr.shop.libs.common.constant.ServiceConstants;
import smr.shop.libs.common.dto.message.ProductMessageModel;
import smr.shop.libs.common.helper.UserHelper;
import smr.shop.libs.grpc.client.ProductGrpcClient;
import smr.shop.libs.grpc.product.ProductGrpcResponse;
import smr.shop.wishlist.service.dto.response.WishlistProductResponse;
import smr.shop.wishlist.service.dto.response.WishlistResponse;
import smr.shop.wishlist.service.exception.WishlistServiceException;
import smr.shop.wishlist.service.mapper.WishlistServiceMapper;
import smr.shop.wishlist.service.model.WishlistEntity;
import smr.shop.wishlist.service.repository.WishlistRepository;
import smr.shop.wishlist.service.service.WishlistService;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class WishlistServiceImpl implements WishlistService {

    private final WishlistServiceMapper wishlistServiceMapper;
    private final WishlistRepository wishlistRepository;
    private final ProductGrpcClient productGrpcClient;

    public WishlistServiceImpl(WishlistServiceMapper wishlistServiceMapper,
                               WishlistRepository wishlistRepository,
                               ProductGrpcClient productGrpcClient) {
        this.wishlistServiceMapper = wishlistServiceMapper;
        this.wishlistRepository = wishlistRepository;
        this.productGrpcClient = productGrpcClient;
    }


//    ----------------------------------- Create or Add -----------------------------------

    @Override
    public void addProductToWishlist(Long productId) {
        UUID userId = UserHelper.getUserId();
        if (wishlistRepository.findByUserIdAndProductId(userId, productId).isEmpty()) {
            ProductGrpcResponse productGrpcResponse = productGrpcClient.getProductByProductId(productId);
            WishlistEntity wishlistEntity = wishlistServiceMapper.productIdAndUserIdToWishlist(productGrpcResponse.getId(), userId);
            wishlistEntity.setId(UUID.randomUUID());
            wishlistEntity.setCreatedAt(ZonedDateTime.now(ServiceConstants.ZONE_ID));
            wishlistRepository.save(wishlistEntity);
        }
    }

//    ----------------------------------- Delete -----------------------------------

    @Override
    public void deleteProductsInWishlist(ProductMessageModel productMessageModel) {
        wishlistRepository.deleteAllByProductId(productMessageModel.getId());
    }

    @Override
    public void deleteWishlist(UUID wishlistId) {
        UUID userId = UserHelper.getUserId();
        WishlistEntity wishlistEntity = findById(wishlistId);
        if (!wishlistEntity.getUserId().equals(userId)) {
            throw new WishlistServiceException("you dont have a permission to delete his wishlist item with id: " + wishlistId, HttpStatus.NOT_FOUND);
        }
        wishlistRepository.delete(wishlistEntity);
    }

//    ----------------------------------- Get -----------------------------------

    @Override
    public List<WishlistResponse> getAllWishlistProducts(Integer page) {
        Pageable pageable = PageRequest.of(page, ServiceConstants.pageSize);
        List<WishlistEntity> wishlistEntityList = wishlistRepository.findAll(pageable).stream().toList();
        return wishlistEntityList.stream().map(wishlistEntity -> {
            WishlistResponse wishlistResponse = wishlistServiceMapper.wishlistEntityToWishlistResponse(wishlistEntity);
            ProductGrpcResponse product = productGrpcClient.getProductByProductId(wishlistEntity.getProductId());
            WishlistProductResponse wishlistProductResponse = wishlistServiceMapper.productGrpcResponseToWishlistProductResponse(product);
            wishlistResponse.setProduct(wishlistProductResponse);
            return wishlistResponse;
        }).toList();
    }

//    ----------------------------------- Extra -----------------------------------

    @Override
    public WishlistEntity findById(UUID wishlistId) {
        return wishlistRepository.findById(wishlistId)
                .orElseThrow(() -> new WishlistServiceException("wishlist not found with id: " + wishlistId, HttpStatus.NOT_FOUND));
    }

}
