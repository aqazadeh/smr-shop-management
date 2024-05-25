package smr.shop.discount.service.model;

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
public class DiscountEntity {
    @Id
    private UUID id;

    private Long shopId;

    private Double amount;

    private ZonedDateTime expireDate;

    private ZonedDateTime createdAt;

    private ZonedDateTime updatedAt;

    @Builder.Default
    private Boolean isDeleted = false;

}
