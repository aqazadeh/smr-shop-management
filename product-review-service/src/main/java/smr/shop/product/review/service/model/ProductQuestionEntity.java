package smr.shop.product.review.service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import smr.shop.product.review.service.model.valueobject.ProductQuestionStatus;

import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class ProductQuestionEntity {
    @Id
    private UUID id;

    private UUID userId;

    private Long productId;

    private String question;

    private UUID questionId;

    @Builder.Default
    private ProductQuestionStatus status = ProductQuestionStatus.CREATED;

    private ZonedDateTime createdAt;

    private ZonedDateTime updatedAt;

}