package smr.shop.cart.service.service;

import smr.shop.cart.service.dto.response.CartItemResponse;
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

    void deleteCart(Long cartId);
    List<CartResponse> addCoupon(String couponCode);
    List<CartResponse> removeCoupon(String couponCode);
    void clearCart(Long userId);
    void deleteCartItem(Long productId);
    List<CartItemResponse> getAllCartItems();
    void increase(Long productId);
    void decrease(Long productId);
    CartItemEntity findById(UUID cartItemId);
    CartEntity findById(Long cartId);
}
