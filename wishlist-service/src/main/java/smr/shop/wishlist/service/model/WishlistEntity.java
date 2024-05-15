package smr.shop.wishlist.service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class WishlistEntity {
    @Id
    private UUID id;

    private UUID userId;

    private Long productId;

    @CreatedDate
    private ZonedDateTime createdAt;

}