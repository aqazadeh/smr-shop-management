package smr.shop.cart.service.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import smr.shop.cart.service.dto.response.CartItemResponse;
import smr.shop.cart.service.dto.response.CartResponse;
import smr.shop.cart.service.exception.CartException;
import smr.shop.cart.service.grpc.CartGrpcClientService;
import smr.shop.cart.service.helper.CartServiceHelper;
import smr.shop.cart.service.mapper.CartServiceMapper;
import smr.shop.cart.service.model.CartEntity;
import smr.shop.cart.service.model.CartItemEntity;
import smr.shop.cart.service.repository.CartItemRepository;
import smr.shop.cart.service.repository.CartRepository;
import smr.shop.cart.service.service.CartService;
import smr.shop.libs.common.Helper.UserHelper;
import smr.shop.libs.common.constant.ServiceConstants;
import smr.shop.libs.common.dto.message.ProductDeleteMessageModel;
import smr.shop.libs.common.dto.message.ProductStockMessageModel;
import smr.shop.libs.grpc.coupon.CouponGrpcResponse;
import smr.shop.libs.grpc.coupon.CouponType;
import smr.shop.libs.grpc.product.ProductGrpcResponse;

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

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final CartServiceMapper cartServiceMapper;
    private final CartGrpcClientService cartGrpcClientService;
    private final CartServiceHelper cartServiceHelper;

    public CartServiceImpl(CartRepository cartRepository,
                           CartItemRepository cartItemRepository,
                           CartServiceMapper cartServiceMapper,
                           CartGrpcClientService cartGrpcClientService,
                           CartServiceHelper cartServiceHelper) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.cartServiceMapper = cartServiceMapper;
        this.cartGrpcClientService = cartGrpcClientService;
        this.cartServiceHelper = cartServiceHelper;
    }

    @Override
    public void addProductToCart(Long productId, UUID stockId) {
        Long userId = UserHelper.getUserId();

        cartGrpcClientService.getProduct(productId);
        cartGrpcClientService.getAttribute(stockId);
        // Exception if not found


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

            cartItem = CartItemEntity.builder()
                    .userId(userId)
                    .cartId(cartId)
                    .productId(productId)
                    .stockId(stockId)
                    .quantity(1)
                    .createdAt(ZonedDateTime.of(LocalDateTime.now(), ZoneId.of(ServiceConstants.UTC)))
                    .updatedAt(ZonedDateTime.of(LocalDateTime.now(), ZoneId.of(ServiceConstants.UTC)))
                    .build();
            cartRepository.save(cart);
        }
        cartItemRepository.save(cartItem);
    }

    @Override
    public CartResponse addCoupon(String couponCode) {
        //userId
        Long userId = UserHelper.getUserId();
        CouponGrpcResponse couponDetailWithCode = cartGrpcClientService.getCouponDetailWithCode(couponCode);
        CartEntity cartEntity = cartRepository.findByUserId(userId).orElseGet(CartEntity::new);
        cartEntity.setCoupon(couponDetailWithCode.getCode());
        cartRepository.save(cartEntity);
        return getCartResponse(cartEntity);
    }

    @Override
    public CartResponse removeCoupon() {
        Long userId = UserHelper.getUserId();
        CartEntity cartEntity = cartRepository.findByUserId(userId).orElseGet(CartEntity::new);
        if (cartEntity.getCoupon() != null) {
            cartEntity.setCoupon(null);
            cartRepository.save(cartEntity);
        }
        return getCartResponse(cartEntity);
    }

    @Override
    public void clearCart() {
        Long userId = UserHelper.getUserId();
        CartEntity cart = cartRepository.findByUserId(userId).orElseGet(() -> CartEntity.builder().id(UUID.randomUUID()).build());
        cartItemRepository.deleteByCartId(cart.getId());

    }

    @Override
    public void deleteCartItem(UUID cartItemId) {
        CartItemEntity cartItemEntity = findItemById(cartItemId);
        Long userId = UserHelper.getUserId();
        if (!cartItemEntity.getUserId().equals(userId)) {
            throw new CartException("Item not found with id:" + cartItemId, HttpStatus.NOT_FOUND);
        }
        cartItemRepository.deleteById(cartItemId);
    }

    @Override
    public CartResponse getAllCartItems() {
        Long userId = UserHelper.getUserId();
        CartEntity cartEntity = cartRepository.findByUserId(userId).orElseGet(() -> CartEntity.builder().id(UUID.randomUUID()).build());
        return getCartResponse(cartEntity);
    }



    @Override
    public void increase(UUID cartItemId) {
        Long userId = UserHelper.getUserId();
        CartItemEntity cartItemEntity = findItemById(cartItemId);
        if (!cartItemEntity.getUserId().equals(userId)) {
            throw new CartException("Item not found with id:" + cartItemId, HttpStatus.NOT_FOUND);
        }
        cartItemEntity.setQuantity(cartItemEntity.getQuantity() + 1);
        cartItemRepository.save(cartItemEntity);


    }

    @Override
    public void decrease(UUID cartItemId) {
        Long userId = UserHelper.getUserId();
        CartItemEntity cartItemEntity = findItemById(cartItemId);
        if (!cartItemEntity.getUserId().equals(userId)) {
            throw new CartException("Item not found with id:" + cartItemId, HttpStatus.NOT_FOUND);
        }
        cartItemEntity.setQuantity(cartItemEntity.getQuantity() - 1);
        cartItemRepository.save(cartItemEntity);

    }

    @Override
    public void removeItemByProduct(ProductDeleteMessageModel productDeleteMessageModel) {
        Long productId = productDeleteMessageModel.getId();
        cartItemRepository.deleteByProductId(productId);
    }

    @Override
    public void removeItemByStock(ProductStockMessageModel productStockMessageModel) {
        UUID stockId = productStockMessageModel.getId();
        cartItemRepository.deleteByStockId(stockId);
    }

    @Override
    public CartItemEntity findItemById(UUID cartItemId) {
        return cartItemRepository.findById(cartItemId).orElseThrow(() -> new CartException("Cart Item Not found with id : " + cartItemId, HttpStatus.NOT_FOUND));

    }

    @Override
    public CartEntity findCartById(UUID cartId) {
        return cartRepository.findById(cartId).orElseThrow(() -> new CartException("cart not found with id : " + cartId, HttpStatus.NOT_FOUND));
    }

    private CartResponse getCartResponse(CartEntity cartEntity) {
        List<CartItemEntity> cartItemEntityList = cartItemRepository.findByCartId(cartEntity.getId());

        List<CartItemResponse> cartItemResponseList = cartItemEntityList.stream().map(cart -> {

            ProductGrpcResponse product = cartGrpcClientService.getProduct(cart.getProductId());

            double newPrice;

            if (cartEntity.getCoupon() != null) {
                CouponGrpcResponse couponDetail = cartGrpcClientService.getCouponDetailWithCode(cartEntity.getCoupon());
                if (couponDetail.getCouponType() == CouponType.SHOP) {

                    if (product.getShopId() == couponDetail.getShopId()) {

                        newPrice = cartServiceHelper.calculateCouponDiscountPrice(product, couponDetail);
                    } else {
                        newPrice = product.getPrice();
                    }
                } else {
                    newPrice = cartServiceHelper.calculateCouponDiscountPrice(product, couponDetail);
                }
            } else {
                newPrice = product.getPrice();
            }
            return cartServiceMapper.cartItemEntityToCartItemResponse(cart, product, newPrice);
        }).toList();

        return cartServiceMapper.cartEntityToCarResponse(cartEntity, cartItemResponseList);
    }
}
