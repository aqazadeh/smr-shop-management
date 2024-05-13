package smr.shop.cart.service.mapper;

import org.springframework.stereotype.Component;
import smr.shop.cart.service.dto.response.CartItemResponse;
import smr.shop.cart.service.model.CartItemEntity;

/**
 * Author: Ali Gadashov
 * Version: v1.0
 * Date: 5/10/2024
 * Time: 1:24 PM
 */
@Component
public class CartServiceMapper {
    public CartItemResponse cartItemEntityToCartItemResponse(CartItemEntity cartItemEntity) {
        return CartItemResponse.builder()
                .productId(cartItemEntity.getProductId())
                .quantity(cartItemEntity.getQuantity())
                .build();
    }

}
