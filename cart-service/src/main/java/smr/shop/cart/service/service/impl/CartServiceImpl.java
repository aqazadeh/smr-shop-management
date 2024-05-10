package smr.shop.cart.service.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import smr.shop.cart.service.dto.response.CartItemResponse;
import smr.shop.cart.service.dto.response.CartResponse;
import smr.shop.cart.service.exception.CartException;
import smr.shop.cart.service.exception.CartItemException;
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
import smr.shop.libs.grpc.coupon.CouponDiscountType;
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
    public void addProductToCart(Long productId, UUID attributeId) {
        Long userId = UserHelper.getUserId();

        cartGrpcClientService.getProduct(productId);
        cartGrpcClientService.getAttribute(attributeId);
        // Exception if not found


        Optional<CartItemEntity> cartItemEntityOptional = cartItemRepository.findByUserIdAndProductIdAndAttributeId(userId, productId, attributeId);
        CartItemEntity cartItem;

        if (cartItemEntityOptional.isPresent()) {
            cartItem = cartItemEntityOptional.get();
            cartItem.setQuantity(cartItem.getQuantity() + 1);

        } else {

            CartEntity cart = cartRepository.findCartEntitiesByUserId(userId).orElseGet(() -> CartEntity.builder().id(UUID.randomUUID()).build());
            UUID cartId = cart.getId();

            cartItem = CartItemEntity.builder()
                    .productId(productId)
                    .attributeId(attributeId)
                    .quantity(1)
                    .createdAt(ZonedDateTime.of(LocalDateTime.now(), ZoneId.of(ServiceConstants.UTC)))
                    .updatedAt(ZonedDateTime.of(LocalDateTime.now(), ZoneId.of(ServiceConstants.UTC)))
                    .build();
        }
        cartItemRepository.save(cartItem);
    }

    @Override
    public CartResponse addCoupon(String couponCode) {
        //userId
        Long userId = UserHelper.getUserId();
        CouponGrpcResponse couponDetailWithCode = cartGrpcClientService.getCouponDetailWithCode(couponCode);
        CartEntity cartEntity = cartRepository.findCartEntitiesByUserId(userId).orElseGet(CartEntity::new);
        cartEntity.setCoupon(couponDetailWithCode.getCode());
        cartRepository.save(cartEntity);

        //TODO calculate coupons amount
        // TODO GET ALL PRODUCTS
        return null;
    }

    @Override
    public CartResponse removeCoupon() {
        Long userId = UserHelper.getUserId();
        CartEntity cartEntity = cartRepository.findCartEntitiesByUserId(userId).orElseGet(CartEntity::new);
        if (cartEntity.getCoupon() != null) {
            cartEntity.setCoupon(null);
            cartRepository.save(cartEntity);
        }
        List<CartItemEntity> cartItemEntityList = cartItemRepository.findByCartId(cartEntity.getId());

        List<CartItemResponse> cartItemResponseList = cartItemEntityList.stream().map(cart -> {

            ProductGrpcResponse product = cartGrpcClientService.getProduct(cart.getProductId());
            return getCartItemResponse(cart, product, product.getPrice());

        }).toList();
        return extracted(cartItemResponseList, cartEntity);
    }

    @Override
    public void clearCart() {
        Long userId = UserHelper.getUserId();
        CartEntity cart = cartRepository.findCartEntitiesByUserId(userId).orElseGet(() -> CartEntity.builder().id(UUID.randomUUID()).build());
        cartRepository.deleteByCartId(cart.getId());

    }

    @Override
    public void deleteCartItem(Long productId, UUID attributeId) {
        Long userId = UserHelper.getUserId();
        cartItemRepository.deleteByUserIdAndProductIdAndAttributeId(userId, productId, attributeId);
    }

    @Override
    public CartResponse getAllCartItems() {
        Long userId = UserHelper.getUserId();
        CartEntity cartEntity = cartRepository.findCartEntitiesByUserId(userId).orElseGet(() -> CartEntity.builder().id(UUID.randomUUID()).build());
        List<CartItemEntity> cartItemEntityList = cartItemRepository.findByCartId(cartEntity.getId());

        List<CartItemResponse> cartItemResponseList = cartItemEntityList.stream().map(cart -> {

            ProductGrpcResponse product = cartGrpcClientService.getProduct(cart.getProductId());

            double newPrice;

            if (cartEntity.getCoupon() != null) {
                CouponGrpcResponse couponDetail = cartGrpcClientService.getCouponDetailWithCode(cartEntity.getCoupon());
                if (couponDetail.getCouponType() == CouponType.SHOP) {

                    if (product.getShopId() == couponDetail.getShopId()) {

                        newPrice = calculateCouponPrice(product, couponDetail);
                    } else {
                        newPrice = product.getPrice();
                    }
                } else {
                    newPrice = calculateCouponPrice(product, couponDetail);
                }
            } else {
                newPrice = product.getPrice();
            }
            return getCartItemResponse(cart, product, newPrice);
        }).toList();

        return extracted(cartItemResponseList, cartEntity);
    }

    private static CartResponse extracted(List<CartItemResponse> cartItemResponseList, CartEntity cartEntity) {
        double totalPrice = cartItemResponseList.stream().mapToDouble(CartItemResponse::getTotalPrice).reduce(0, Double::sum);
        double totalDiscountPrice = cartItemResponseList.stream().mapToDouble(CartItemResponse::getDiscountPrice).reduce(0, Double::sum);

        return CartResponse.builder()
                .id(cartEntity.getId())
                .couponCode(cartEntity.getCoupon())
                .items(cartItemResponseList)
                .price(totalPrice)
                .discountPrice(totalDiscountPrice)
                .build();
    }

    private static CartItemResponse getCartItemResponse(CartItemEntity cart, ProductGrpcResponse product, double newPrice) {
        return CartItemResponse.builder()
                .productId(product.getId())
                .name(product.getName())
                .quantity(cart.getQuantity())
                .unitPrice(product.getPrice())
                .totalPrice(cart.getQuantity() * product.getPrice())
                .discountPrice(newPrice * cart.getQuantity())
                .attributeName(product.getProductAttribute().getName())
                .thumbnail(product.getThumbnail())
                .build();
    }

    private static double calculateCouponPrice(ProductGrpcResponse product, CouponGrpcResponse couponDetail) {
        double newPrice;
        if (couponDetail.getCouponTypeValue() == CouponDiscountType.AMOUNT_VALUE) {
            newPrice = product.getPrice() - couponDetail.getAmount();
        } else {
            double amount = (product.getPrice() / 100) * couponDetail.getPercentage();
            if (amount > couponDetail.getMaxDiscountPrice()) {
                newPrice = product.getPrice() - couponDetail.getMaxDiscountPrice();
            } else {
                newPrice = product.getPrice() - amount;
            }
        }
        return newPrice;
    }


    @Override
    public void increase(Long productId, UUID attributeId) {
        Long userId = UserHelper.getUserId();
        Optional<CartItemEntity> cartItemEntity = cartItemRepository.findByUserIdAndProductIdAndAttributeId(userId, productId, attributeId);
        if (cartItemEntity.isPresent()) {
            cartItemEntity.get().setQuantity(cartItemEntity.get().getQuantity() + 1);
            cartItemRepository.save(cartItemEntity.get());
        }

    }

    @Override
    public void decrease(Long productId, UUID attributeId) {
        Long userId = UserHelper.getUserId();
        Optional<CartItemEntity> cartItemEntity = cartItemRepository.findByUserIdAndProductIdAndAttributeId(userId, productId, attributeId);
        if (cartItemEntity.isPresent()) {
            cartItemEntity.get().setQuantity(cartItemEntity.get().getQuantity() - 1);
            cartItemRepository.save(cartItemEntity.get());
        }
    }

    @Override
    public CartItemEntity findItemById(UUID cartItemId) {
        return cartItemRepository.findById(cartItemId).orElseThrow(() -> new CartItemException("Cart Item Not found With id : " + cartItemId, HttpStatus.NOT_FOUND));

    }

    @Override
    public CartEntity findCartById(UUID cartId) {
        return cartRepository.findById(cartId).orElseThrow(() -> new CartException("Cart Not found With id : " + cartId, HttpStatus.NOT_FOUND));
    }
}
