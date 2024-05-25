package smr.shop.cart.service.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
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

import java.security.Principal;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
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

    public CartServiceImpl(CartRepository cartRepository,
                           CartItemRepository cartItemRepository,
                           CartServiceMapper cartServiceMapper,
                           CartServiceHelper cartServiceHelper,
                           ProductGrpcClient productGrpcClient,
                           ProductStockGrpcClient productStockGrpcClient,
                           CouponGrpcClient couponGrpcClient) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.cartServiceMapper = cartServiceMapper;
        this.cartServiceHelper = cartServiceHelper;
        this.productGrpcClient = productGrpcClient;
        this.productStockGrpcClient = productStockGrpcClient;
        this.couponGrpcClient = couponGrpcClient;
    }


//    ----------------------------------- Create or Add -----------------------------------

    @Override
    @Transactional
    public void addProductToCart(Long productId, UUID stockId) {
        UUID userId = UserHelper.getUserId();
        if(productId == 0) {

            throw new CartServiceException("Test exception", HttpStatus.BAD_REQUEST);
        }
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
    }

    @Override
    @Transactional
    public void addCoupon(String couponCode) {
        UUID userId = UserHelper.getUserId();
        CouponGrpcResponse couponDetailWithCode = couponGrpcClient.getCouponByCode(couponCode);
        CartEntity cartEntity = cartRepository.findByUserId(userId).orElseGet(CartEntity::new);
        cartEntity.setCoupon(couponDetailWithCode.getCode());
        cartRepository.save(cartEntity);
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
    }

    @Override
    @Transactional
    public void clearCart() {
        UUID userId = UserHelper.getUserId();
        Optional<CartEntity> cartEntity = cartRepository.findByUserId(userId);
        cartEntity.ifPresent(entity -> cartItemRepository.deleteAllByCartId(entity.getId()));

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
        }else {
            deleteCartItem(cartItemEntity.getId());
        }

    }

    @Override
    @Transactional
    public void removeItemByProduct(ProductMessageModel productMessageModel) {
        Long productId = productMessageModel.getId();
        cartItemRepository.deleteByProductId(productId);
    }

    @Override
    @Transactional
    public void removeItemByStock(ProductStockMessageModel productStockMessageModel) {
        UUID stockId = productStockMessageModel.getId();
        cartItemRepository.deleteByStockId(stockId);
    }

    @Override
    @Transactional
    public void removeCouponInItems(CouponMessageModel message) {
        cartRepository.removeCouponInCart(message.getCode());
    }

//    ----------------------------------- Get -----------------------------------

    @Override
    public CartResponse getUserCart() {
        UUID userId = UserHelper.getUserId();
        CartEntity cartEntity = cartRepository.findByUserId(userId).orElseGet(() -> CartEntity.builder().id(UUID.randomUUID()).build());
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
