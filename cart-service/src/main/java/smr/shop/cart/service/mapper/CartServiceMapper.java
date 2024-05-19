package smr.shop.cart.service.mapper;

import org.springframework.stereotype.Component;
import smr.shop.cart.service.dto.response.CartItemResponse;
import smr.shop.cart.service.dto.response.CartResponse;
import smr.shop.cart.service.model.CartEntity;
import smr.shop.cart.service.model.CartItemEntity;
import smr.shop.libs.grpc.product.ProductGrpcResponse;
import smr.shop.libs.grpc.product.stock.ProductStockGrpcResponse;

import java.math.BigDecimal;
import java.util.List;

/**
 * Author: Ali Gadashov
 * Version: v1.0
 * Date: 5/10/2024
 * Time: 1:24 PM
 */
@Component
public class CartServiceMapper {

    public CartItemResponse cartItemEntityToCartItemResponse(CartItemEntity cart, ProductGrpcResponse product, ProductStockGrpcResponse productStockGrpcResponse, BigDecimal discountPrice) {
        return CartItemResponse.builder()
                .id(cart.getId())
                .productId(product.getId())
                .name(product.getName())
                .quantity(cart.getQuantity())
                .unitPrice(product.getPrice())
                .discountPrice(discountPrice.multiply(BigDecimal.valueOf(cart.getQuantity())))
                .totalPrice(cart.getQuantity() * product.getPrice())
                .attributeName(productStockGrpcResponse.getName())
                .thumbnail(product.getThumbnail())
                .build();
    }


    public CartResponse cartEntityToCarResponse(CartEntity cartEntity, List<CartItemResponse> cartItemResponseList) {
        double totalPrice = cartItemResponseList.stream().mapToDouble(CartItemResponse::getTotalPrice).reduce(0, Double::sum);
        BigDecimal totalDiscountPrice = cartItemResponseList.stream().map(CartItemResponse::getDiscountPrice).reduce(BigDecimal.ZERO, BigDecimal::add);

        return CartResponse.builder()
                .id(cartEntity.getId())
                .couponCode(cartEntity.getCoupon())
                .items(cartItemResponseList)
                .price(totalPrice)
                .discountPrice(totalDiscountPrice)
                .build();
    }

}
