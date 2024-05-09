package smr.shop.cart.service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

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

    private Long cartId;

    private Long userId;

    private Long productId;

    private UUID attributeId;

    private Integer quantity;

    private ZonedDateTime createdAt;

    private ZonedDateTime updatedAt;

}
