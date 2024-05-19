package smr.shop.coupon.service.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import lombok.*;
import smr.shop.libs.common.model.valueobject.CouponDiscountType;
import smr.shop.libs.common.model.valueobject.CouponType;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class CouponEntity {
    @Id
    private UUID id;

    @Enumerated(EnumType.STRING)
    private CouponType type;

    private Long shopId;

    private String code;

    private String details;

    @Enumerated(EnumType.STRING)
    private CouponDiscountType discountType;

    private BigDecimal amount;

    private Short percentage;

    private BigDecimal maxDiscountPrice;

    private ZonedDateTime expirationTime;

    @Builder.Default
    private Boolean isDeleted = Boolean.FALSE;

    private ZonedDateTime createdAt;

    private ZonedDateTime updatedAt;

}