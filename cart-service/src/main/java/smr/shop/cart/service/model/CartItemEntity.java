package smr.shop.cart.service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class CartItemEntity {
    @Id
    private UUID id;

    private UUID cartId;

    private UUID userId;

    private Long productId;

    private UUID stockId;

    private Integer quantity;

    private ZonedDateTime createdAt;

    private ZonedDateTime updatedAt;

}
