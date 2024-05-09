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

    private Long userId;

    private UUID couponId;

    private ZonedDateTime createdAt;

}

/*
    Bu entity istifadecilerin istifade etdiyi kuponlari tutur.
    eger istifade edilmeye calisilan kupon daha onceden istifade olunubsa daha sonra istifade edile bilmez. Bunu nezere alin
 */