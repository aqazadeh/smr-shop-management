package smr.shop.product.review.service.model;

import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class ProductReviewEntity {
    @Id
    private UUID id;

    private UUID userId;

    private Long productId;

    private Byte rating;

    private String comment;

    @ElementCollection
    private List<String> images;

    private ZonedDateTime createdAt;

    private ZonedDateTime updatedAt;

}