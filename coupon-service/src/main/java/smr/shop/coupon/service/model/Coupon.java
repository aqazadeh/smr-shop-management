package smr.shop.coupon.service.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import smr.shop.coupon.service.model.valueobject.CouponDiscountType;
import smr.shop.coupon.service.model.valueobject.CouponType;

import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Coupon {
    @Id
    private UUID id;

    @Enumerated(EnumType.STRING)
    private CouponType type;

    private Long shopId;

    private String code;

    private String details;

    @Enumerated(EnumType.STRING)
    private CouponDiscountType discountType;

    private Double amount;

    private Float percentage;

    private ZonedDateTime endDate;

    private Boolean isActive = true;

    @CreatedDate
    private ZonedDateTime createdAt;

    @LastModifiedDate
    private ZonedDateTime updatedAt;

}


/*
    Hem admin hem de magaza sahibleri yeni bir coupon yarada biler.
    Eger kupon tipi all'dirsa butun magazalarda,
    eger Shopdursa sadece uygun magazada mehsullarinda istifade oluna biler
*/