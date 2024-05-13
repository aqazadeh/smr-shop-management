package smr.shop.cart.service.model;

import jakarta.persistence.*;
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
public class CartEntity {
    @Id
    private UUID id;

    private String coupon;

    private ZonedDateTime createdAt;

    private ZonedDateTime updatedAt;
}
