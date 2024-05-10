package smr.shop.coupon.service.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Value;
import smr.shop.coupon.service.model.valueobject.CouponDiscountType;
import smr.shop.coupon.service.model.valueobject.CouponType;

import java.time.ZonedDateTime;

/**
 * Author: Ali Gadashov
 * Version: v1.0
 * Date: 5/9/2024
 * Time: 3:01 PM
 */

@Value
public class CouponUpdateRequest {

    @NotBlank
    CouponType type;

    @NotBlank
    String code;

    @NotBlank
    String details;

    @NotBlank
    CouponDiscountType discountType;

    @NotBlank
    Double amount;

    @NotBlank
    Float percentage;

    @NotBlank
    Double maxDiscountPrice;

    @NotBlank
    ZonedDateTime endDate;

    @NotBlank
    ZonedDateTime updatedAt;

}
