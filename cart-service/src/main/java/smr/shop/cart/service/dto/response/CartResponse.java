package smr.shop.cart.service.dto.response;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

/**
 * Author: Ali Gadashov
 * Version: v1.0
 * Date: 5/10/2024
 * Time: 1:11 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartResponse implements Serializable {
    private UUID id;

    private String couponCode;

    private Double price;

    private BigDecimal discountPrice;

    private List<CartItemResponse> items;
}
