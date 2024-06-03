package smr.shop.cart.service.service.impl;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smr.discount.libs.grpc.product.discount.DiscountGrpcResponse;
import smr.shop.cart.service.dto.response.CartItemResponse;
import smr.shop.cart.service.dto.response.CartResponse;
import smr.shop.cart.service.exception.CartServiceException;
import smr.shop.cart.service.helper.CartServiceHelper;
import smr.shop.cart.service.mapper.CartServiceMapper;
import smr.shop.cart.service.model.CartEntity;
import smr.shop.cart.service.model.CartItemEntity;
import smr.shop.cart.service.repository.CartItemRepository;
import smr.shop.cart.service.repository.CartRepository;
import smr.shop.cart.service.service.CartService;
import smr.shop.libs.common.constant.CacheConstants;
import smr.shop.libs.common.constant.ServiceConstants;
import smr.shop.libs.common.dto.message.CouponMessageModel;
import smr.shop.libs.common.dto.message.ProductMessageModel;
import smr.shop.libs.common.dto.message.ProductStockMessageModel;
import smr.shop.libs.common.helper.Money;
import smr.shop.libs.common.helper.UserHelper;
import smr.shop.libs.common.model.valueobject.CouponType;
import smr.shop.libs.grpc.client.CouponGrpcClient;
import smr.shop.libs.grpc.client.ProductGrpcClient;
import smr.shop.libs.grpc.client.ProductStockGrpcClient;
import smr.shop.libs.grpc.coupon.CouponGrpcResponse;
import smr.shop.libs.grpc.product.ProductGrpcResponse;
import smr.shop.libs.grpc.product.stock.ProductStockGrpcResponse;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

/**
 * Author: Ali Gadashov
 * Version: v1.0
 * Date: 5/10/2024
 * Time: 1:28 PM
 */


@Service
public class CartServiceImpl implements CartService {

    // repository
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    // mapper
    private final CartServiceMapper cartServiceMapper;

    // helper
    private final CartServiceHelper cartServiceHelper;

    // rpc
    private final ProductGrpcClient productGrpcClient;
    private final ProductStockGrpcClient productStockGrpcClient;
    private final CouponGrpcClient couponGrpcClient;


    // cache
    private final CacheManager cacheManager;

    public CartServiceImpl(CartRepository cartRepository,
                           CartItemRepository cartItemRepository,
                           CartServiceMapper cartServiceMapper,
                           CartServiceHelper cartServiceHelper,
                           ProductGrpcClient productGrpcClient,
                           ProductStockGrpcClient productStockGrpcClient,
                           CouponGrpcClient couponGrpcClient,
                           CacheManager cacheManager) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.cartServiceMapper = cartServiceMapper;
        this.cartServiceHelper = cartServiceHelper;
        this.productGrpcClient = productGrpcClient;
        this.productStockGrpcClient = productStockGrpcClient;
        this.couponGrpcClient = couponGrpcClient;
        this.cacheManager = cacheManager;
    }


//    ----------------------------------- Create or Add -----------------------------------

    @Override
    @Transactional
    public void addProductToCart(Long productId, UUID stockId) {
        UUID userId = UserHelper.getUserId();
        ProductGrpcResponse productGrpcResponse = productGrpcClient.getProductByProductId(productId);
        ProductStockGrpcResponse productStockGrpcResponse = productStockGrpcClient.getProductByStockId(stockId.toString());

        if (productStockGrpcResponse.getProductId() != productGrpcResponse.getId()) {
            throw new CartServiceException("this attribute not found in product", HttpStatus.BAD_REQUEST);
        }

        Optional<CartItemEntity> cartItemEntityOptional = cartItemRepository.findByUserIdAndProductIdAndStockId(userId, productId, stockId);
        CartItemEntity cartItem;

        if (cartItemEntityOptional.isPresent()) {
            cartItem = cartItemEntityOptional.get();
            cartItem.setQuantity(cartItem.getQuantity() + 1);

        } else {

            CartEntity cart = cartRepository.findByUserId(userId)
                    .orElseGet(() -> CartEntity.builder()
                            .id(UUID.randomUUID())
                            .userId(userId).build());
            UUID cartId = cart.getId();
            cartRepository.save(cart);

            cartItem = CartItemEntity.builder()
                    .id(UUID.randomUUID())
                    .userId(userId)
                    .cartId(cartId)
                    .productId(productId)
                    .stockId(stockId)
                    .quantity(1)
                    .createdAt(ZonedDateTime.of(LocalDateTime.now(), ZoneId.of(ServiceConstants.UTC)))
                    .updatedAt(ZonedDateTime.of(LocalDateTime.now(), ZoneId.of(ServiceConstants.UTC)))
                    .build();
        }
        cartItemRepository.save(cartItem);
        Objects.requireNonNull(cacheManager.getCache(CacheConstants.USER_CART)).evict(userId);
    }

    @Override
    @Transactional
    public void addCoupon(String couponCode) {
        UUID userId = UserHelper.getUserId();
        CouponGrpcResponse couponDetailWithCode = couponGrpcClient.getCouponByCode(couponCode);
        CartEntity cartEntity = cartRepository.findByUserId(userId).orElseGet(CartEntity::new);
        cartEntity.setCoupon(couponDetailWithCode.getCode());
        cartRepository.save(cartEntity);
        Objects.requireNonNull(cacheManager.getCache(CacheConstants.USER_CART)).evict(userId);
    }

    @Override
    @Transactional
    public void increase(UUID cartItemId) {
        UUID userId = UserHelper.getUserId();
        CartItemEntity cartItemEntity = findItemById(cartItemId);
        if (!cartItemEntity.getUserId().equals(userId)) {
            throw new CartServiceException("Item not found with id:" + cartItemId, HttpStatus.NOT_FOUND);
        }
        cartItemEntity.setQuantity(cartItemEntity.getQuantity() + 1);
        cartItemRepository.save(cartItemEntity);
        Objects.requireNonNull(cacheManager.getCache(CacheConstants.USER_CART)).evict(userId);

    }

//    ----------------------------------- Delete -----------------------------------

    @Override
    @Transactional
    public void removeCoupon() {

        UUID userId = UserHelper.getUserId();
        CartEntity cartEntity = cartRepository.findByUserId(userId).orElseGet(CartEntity::new);
        if (cartEntity.getCoupon() != null) {
            cartEntity.setCoupon(null);
            cartRepository.save(cartEntity);
        }
        Objects.requireNonNull(cacheManager.getCache(CacheConstants.USER_CART)).evict(userId);
    }

    @Override
    @Transactional
    public void clearCart() {
        UUID userId = UserHelper.getUserId();
        Optional<CartEntity> cartEntity = cartRepository.findByUserId(userId);
        cartEntity.ifPresent(entity -> cartItemRepository.deleteAllByCartId(entity.getId()));
        Objects.requireNonNull(cacheManager.getCache(CacheConstants.USER_CART)).evict(userId);

    }

    @Override
    @Transactional
    public void deleteCartItem(UUID cartItemId) {
        CartItemEntity cartItemEntity = findItemById(cartItemId);
        UUID userId = UserHelper.getUserId();
        if (!cartItemEntity.getUserId().equals(userId)) {
            throw new CartServiceException("Item not found with id:" + cartItemId, HttpStatus.NOT_FOUND);
        }
        cartItemRepository.deleteById(cartItemId);
        Objects.requireNonNull(cacheManager.getCache(CacheConstants.USER_CART)).evict(userId);

    }

    @Override
    @Transactional
    public void decrease(UUID cartItemId) {
        UUID userId = UserHelper.getUserId();
        CartItemEntity cartItemEntity = findItemById(cartItemId);
        if (!cartItemEntity.getUserId().equals(userId)) {
            throw new CartServiceException("Item not found with id:" + cartItemId, HttpStatus.NOT_FOUND);
        }
        if (cartItemEntity.getQuantity() > 1) {
            cartItemEntity.setQuantity(cartItemEntity.getQuantity() - 1);
            cartItemRepository.save(cartItemEntity);
        } else {
            deleteCartItem(cartItemEntity.getId());
        }
        Objects.requireNonNull(cacheManager.getCache(CacheConstants.USER_CART)).evict(userId);

    }

    @Override
    @Transactional
    public void removeItemByProduct(ProductMessageModel productMessageModel) {
        Long productId = productMessageModel.getId();
        cartItemRepository.findByProductId(productId).forEach(item -> {
            Objects.requireNonNull(cacheManager.getCache(CacheConstants.USER_CART)).evict(item.getUserId().toString());
            cartItemRepository.delete(item);
        });
    }

    @Override
    @Transactional
    public void removeItemByStock(ProductStockMessageModel productStockMessageModel) {
        UUID stockId = productStockMessageModel.getId();
        cartItemRepository.findByStockId(stockId).forEach(item -> {
            Objects.requireNonNull(cacheManager.getCache(CacheConstants.USER_CART)).evict(item.getUserId().toString());
            cartItemRepository.delete(item);
        });
    }

    @Override
    @Transactional
    public void removeCouponInItems(CouponMessageModel message) {
        cartRepository.findByCoupon(message.getCode()).forEach(cart -> {
            Objects.requireNonNull(cacheManager.getCache(CacheConstants.USER_CART)).evict(cart.getUserId().toString());
            cartRepository.removeCouponInCart(cart.getId());
        });
    }

//    ----------------------------------- Get -----------------------------------

    @Override
    @Cacheable(value = CacheConstants.USER_CART, key = "#userId.toString()", sync = true)
    public CartResponse getUserCart(UUID userId) {
        Optional<CartEntity> cart = cartRepository.findByUserId(userId);
        CartEntity cartEntity;
        if (cart.isEmpty()) {
            cartEntity = CartEntity.builder().id(UUID.randomUUID()).userId(userId).build();
            cartRepository.save(cartEntity);
        } else {
            cartEntity = cart.get();
        }
        return getCartResponse(cartEntity);
    }

//    ----------------------------------- Extra -----------------------------------

    @Override
    public CartItemEntity findItemById(UUID cartItemId) {
        return cartItemRepository.findById(cartItemId).orElseThrow(() -> new CartServiceException("Cart Item Not found with id : " + cartItemId, HttpStatus.NOT_FOUND));

    }

    @Override
    public CartEntity findCartById(UUID cartId) {
        return cartRepository.findById(cartId).orElseThrow(() -> new CartServiceException("cart not found with id : " + cartId, HttpStatus.NOT_FOUND));
    }

    private CartResponse getCartResponse(CartEntity cartEntity) {
        List<CartItemEntity> cartItemEntityList = cartItemRepository.findByCartId(cartEntity.getId());

        List<CartItemResponse> cartItemResponseList = cartItemEntityList.stream().map(cart -> {

            ProductGrpcResponse product = productGrpcClient.getProductByProductId(cart.getProductId());
            ProductStockGrpcResponse productStockGrpcResponse = productStockGrpcClient.getProductByStockId(cart.getStockId().toString());
            DiscountGrpcResponse discountGrpcResponse = product.getDiscount();

            Money amount;

            if (cartEntity.getCoupon() != null) {
                CouponGrpcResponse couponDetail = couponGrpcClient.getCouponByCode(cartEntity.getCoupon());
                if (CouponType.valueOf(couponDetail.getCouponType()) == CouponType.SHOP) {

                    if (product.getShopId() == couponDetail.getShopId()) {
                        amount = cartServiceHelper.calculateCouponDiscountPrice(product, couponDetail);
                    } else {
                        amount = Money.valueOf(product.getPrice());
                    }
                } else {
                    amount = cartServiceHelper.calculateCouponDiscountPrice(product, couponDetail);
                }
            } else {
                amount = Money.ZERO;
            }
            return cartServiceMapper.cartItemEntityToCartItemResponse(cart, product, productStockGrpcResponse, amount.getAmount());
        }).toList();

        return cartServiceMapper.cartEntityToCarResponse(cartEntity, cartItemResponseList);
    }
}
