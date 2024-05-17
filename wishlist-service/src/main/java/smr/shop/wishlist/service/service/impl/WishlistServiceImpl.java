package smr.shop.wishlist.service.service.impl;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import smr.shop.libs.common.helper.UserHelper;
import smr.shop.libs.common.constant.ServiceConstants;
import smr.shop.libs.common.dto.message.ProductDeleteMessageModel;
import smr.shop.libs.grpc.product.ProductGrpcResponse;
import smr.shop.wishlist.service.dto.response.WishlistProductResponse;
import smr.shop.wishlist.service.dto.response.WishlistResponse;
import smr.shop.wishlist.service.exception.WishlistException;
import smr.shop.wishlist.service.grpc.WishlistGrpcClientService;
import smr.shop.wishlist.service.mapper.WishlistServiceMapper;
import smr.shop.wishlist.service.model.WishlistEntity;
import smr.shop.wishlist.service.repository.WishlistRepository;
import smr.shop.wishlist.service.service.WishlistService;

import java.util.List;
import java.util.UUID;

@Service
public class WishlistServiceImpl implements WishlistService {

    private final WishlistServiceMapper wishlistServiceMapper;
    private final WishlistRepository wishlistRepository;
    private final WishlistGrpcClientService wishlistGrpcClientService;

    public WishlistServiceImpl(WishlistServiceMapper wishlistServiceMapper,
                               WishlistRepository wishlistRepository,
                               WishlistGrpcClientService wishlistGrpcClientService) {
        this.wishlistServiceMapper = wishlistServiceMapper;
        this.wishlistRepository = wishlistRepository;
        this.wishlistGrpcClientService = wishlistGrpcClientService;
    }


    @Override
    public void addProductToWishlist(Long productId) {
        UUID userId = UserHelper.getUserId();
        if (wishlistRepository.findByUserIdAndProductId(userId, productId).isEmpty()) {
            ProductGrpcResponse productGrpcResponse = wishlistGrpcClientService.getProduct(productId);
            WishlistEntity wishlistEntity = wishlistServiceMapper.productIdAndUserIdToWishlist(productGrpcResponse.getId(), userId);
            wishlistRepository.save(wishlistEntity);
        }
    }


    @Override
    public void deleteProductInUserWishlist(Long productId) {
        UUID userId = UserHelper.getUserId();
        if (wishlistRepository.findByUserIdAndProductId(userId, productId).isEmpty()) {
            throw new WishlistException("can not found wishlist with product id: " + productId, HttpStatus.NOT_FOUND);
        }

        wishlistRepository.deleteByUserIdAndProductId(userId, productId);
    }

    @Override
    public void deleteProductsInWishlist(ProductDeleteMessageModel productDeleteMessageModel) {
        wishlistRepository.deleteByProductId(productDeleteMessageModel.getId());
    }

    @Override
    public List<WishlistResponse> getAllWishlistProducts(Integer page) {
        Pageable pageable = PageRequest.of(page, ServiceConstants.pageSize);
        List<WishlistEntity> wishlistEntityList = wishlistRepository.findAll(pageable).stream().toList();

        return wishlistEntityList.stream().map(wishlistEntity -> {
            WishlistResponse wishlistResponse = wishlistServiceMapper.wishlistEntityToWishlistResponse(wishlistEntity);

            ProductGrpcResponse product = wishlistGrpcClientService.getProduct(wishlistEntity.getProductId());

            WishlistProductResponse wishlistProductResponse = wishlistServiceMapper.productGrpcResponseToWishlistProductResponse(product);

            wishlistResponse.setProduct(wishlistProductResponse);

            return wishlistResponse;
        }).toList();
    }

}
