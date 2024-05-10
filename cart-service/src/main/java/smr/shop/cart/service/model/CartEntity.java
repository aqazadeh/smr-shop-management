package smr.shop.cart.service.model;

import jakarta.persistence.*;
import lombok.*;
import smr.shop.cart.service.model.valueobject.CartStatus;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    private List<UUID> coupons;

    @Enumerated(EnumType.STRING)
    private CartStatus status;

    private ZonedDateTime createdAt;

    private ZonedDateTime updatedAt;
}
