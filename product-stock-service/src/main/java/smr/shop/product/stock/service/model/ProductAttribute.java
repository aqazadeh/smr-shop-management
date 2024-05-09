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
public class ProductAttribute {
    @Id
    private UUID id;

    private String name;

    private Long productId;

    private ZonedDateTime createdAt;

    private ZonedDateTime updatedAt;

}