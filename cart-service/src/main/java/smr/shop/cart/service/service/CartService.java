package smr.shop.cart.service.service;

import smr.shop.cart.service.dto.response.CartResponse;
import smr.shop.cart.service.model.CartEntity;
import smr.shop.cart.service.model.CartItemEntity;

import java.util.List;
import java.util.UUID;

/**
 * Author: Ali Gadashov
 * Version: v1.0
 * Date: 5/10/2024
 * Time: 1:28 PM
 */

public interface CartService {

    void addProductToCart(Long productId, UUID attributeId);

    CartResponse addCoupon(String couponCode);

    CartResponse removeCoupon();

    void clearCart();

    void deleteCartItem(Long productId, UUID attributeId);

    CartResponse getAllCartItems();

    void increase(Long productId, UUID attributeId);

    void decrease(Long productId, UUID attributeId);

    CartItemEntity findItemById(UUID cartItemId);

    CartEntity findCartById(UUID cartId);
}
