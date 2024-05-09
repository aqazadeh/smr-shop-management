package smr.shop.product.review.service.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class ProductReview {
    @Id
    private UUID id;

    private Long userId;

    private Long productId;

    private Byte rating;

    private String comment;

    @ElementCollection
    private List<String> images;

    private ZonedDateTime createdAt;

    private ZonedDateTime updatedAt;

}