package smr.shop.cart.service.dto.response;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

/**
 * Author: Ali Gadashov
 * Version: v1.0
 * Date: 5/10/2024
 * Time: 1:14 PM
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemResponse implements Serializable {
    private UUID id;

    private Long productId;

    private String name;

    private Integer quantity;

    private Double unitPrice;

    private Double totalPrice;

    private BigDecimal discountPrice;

    private String attributeName;

    private String thumbnail;

}
