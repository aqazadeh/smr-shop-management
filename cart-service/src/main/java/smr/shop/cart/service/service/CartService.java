package smr.shop.cart.service.service;

import smr.shop.cart.service.dto.response.CartResponse;
import smr.shop.cart.service.model.CartEntity;
import smr.shop.cart.service.model.CartItemEntity;
import smr.shop.libs.common.dto.message.ProductDeleteMessageModel;
import smr.shop.libs.common.dto.message.ProductStockMessageModel;

import java.util.UUID;

/**
 * Author: Ali Gadashov
 * Version: v1.0
 * Date: 5/10/2024
 * Time: 1:28 PM
 */

public interface CartService {

    void addProductToCart(Long productId, UUID stockId);

    CartResponse addCoupon(String couponCode);

    CartResponse removeCoupon();

    void clearCart();

    void deleteCartItem(UUID cartItemId);

    CartResponse getAllCartItems();

    void increase(UUID cartItemId);

    void decrease(UUID cartItemId);


    void removeItemByProduct(ProductDeleteMessageModel productDeleteMessageModel);

    void removeItemByStock(ProductStockMessageModel productStockMessageModel);

    CartItemEntity findItemById(UUID cartItemId);

    CartEntity findCartById(UUID cartId);
}
