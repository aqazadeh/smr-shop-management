package smr.shop.cart.service.dto.response;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * Author: Ali Gadashov
 * Version: v1.0
 * Date: 5/10/2024
 * Time: 1:11 PM
 */
@Value
@Builder
public class CartResponse {
    Long id;

    List<UUID> coupons;

    BigDecimal price;
    BigDecimal discountedPrice;
    List<CartItemResponse> cartItems;
}