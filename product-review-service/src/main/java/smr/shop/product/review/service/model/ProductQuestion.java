package smr.shop.product.review.service.model;

import jakarta.persistence.*;
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
public class ProductQuestion {
    @Id
    private UUID id;

    private Long userId;

    private Long productId;

    private String question;

    private UUID questionId;

    private ZonedDateTime createdAt;

    private ZonedDateTime updatedAt;

}