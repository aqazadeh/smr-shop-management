package smr.shop.flash.service.model;

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
public class FlashItemEntity {
    @Id
    private UUID id;

    private Long flashId;

    private Long productId;

    private ZonedDateTime createdAt;

    private ZonedDateTime updatedAt;

}