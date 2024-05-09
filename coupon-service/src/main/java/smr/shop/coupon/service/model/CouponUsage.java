package smr.shop.coupon.service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
public class CouponUsage {
    @Id
    private UUID id;

    private Long userId;

    @ManyToOne
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    @CreatedDate
    private ZonedDateTime createdAt;

}

/*
    Bu entity istifadecilerin istifade etdiyi kuponlari tutur.
    eger istifade edilmeye calisilan kupon daha onceden istifade olunubsa daha sonra istifade edile bilmez. Bunu nezere alin
 */