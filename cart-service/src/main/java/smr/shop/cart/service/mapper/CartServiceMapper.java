package smr.shop.cart.service.mapper;

import org.springframework.stereotype.Component;
import smr.shop.cart.service.dto.response.CartItemResponse;
import smr.shop.cart.service.dto.response.CartResponse;
import smr.shop.cart.service.model.CartEntity;
import smr.shop.cart.service.model.CartItemEntity;
import smr.shop.libs.grpc.product.ProductGrpcResponse;

import java.util.List;

/**
 * Author: Ali Gadashov
 * Version: v1.0
 * Date: 5/10/2024
 * Time: 1:24 PM
 */
@Component
public class CartServiceMapper {

    public CartItemResponse cartItemEntityToCartItemResponse(CartItemEntity cart, ProductGrpcResponse product, Double discountPrice) {
        return CartItemResponse.builder()
                .productId(product.getId())
                .name(product.getName())
                .quantity(cart.getQuantity())
                .unitPrice(product.getPrice())
                .discountPrice(discountPrice * cart.getQuantity())
                .totalPrice(cart.getQuantity() * product.getPrice())
                .attributeName(product.getStock().getAttributeName())
//                TODO Check here
                .thumbnail(product.getThumbnail())
                .build();
    }



    public CartResponse cartEntityToCarResponse(CartEntity cartEntity, List<CartItemResponse> cartItemResponseList) {
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

}
