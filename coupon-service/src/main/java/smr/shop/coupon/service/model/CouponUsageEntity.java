package smr.shop.coupon.service.model;

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
public class CouponUsageEntity {
    @Id
    private UUID id;

    private UUID userId;

    private UUID couponId;

    private ZonedDateTime createdAt;

}