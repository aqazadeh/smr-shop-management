package smr.shop.product.stock.service.model;

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
public class ProductStockEntity {
    @Id
    private UUID id;

    private Long productId;

    private String attributeName;

    private Integer quantity;

    private ZonedDateTime createdAt;

    private ZonedDateTime updatedAt;
}