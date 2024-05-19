package smr.shop.cart.service.service;

import smr.shop.cart.service.dto.response.CartResponse;
import smr.shop.cart.service.model.CartEntity;
import smr.shop.cart.service.model.CartItemEntity;
import smr.shop.libs.common.dto.message.CouponMessageModel;
import smr.shop.libs.common.dto.message.ProductMessageModel;
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

    void addCoupon(String couponCode);

    void removeCoupon();

    void clearCart();

    void deleteCartItem(UUID cartItemId);

    CartResponse getUserCart();

    void increase(UUID cartItemId);

    void decrease(UUID cartItemId);


    void removeItemByProduct(ProductMessageModel productMessageModel);

    void removeItemByStock(ProductStockMessageModel productStockMessageModel);

    CartItemEntity findItemById(UUID cartItemId);

    CartEntity findCartById(UUID cartId);

    void removeCouponInItems(CouponMessageModel message);
}
