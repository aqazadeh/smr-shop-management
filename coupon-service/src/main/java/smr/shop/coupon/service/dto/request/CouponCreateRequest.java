package smr.shop.coupon.service.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Value;
import smr.shop.coupon.service.model.valueObject.CouponDiscountType;
import smr.shop.coupon.service.model.valueObject.CouponType;

import java.time.ZonedDateTime;

/**
 * Author: Ali Gadashov
 * Version: v1.0
 * Date: 5/9/2024
 * Time: 3:01 PM
 */
@Value
public class CouponCreateRequest {

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
    ZonedDateTime createdAt;

    @NotBlank
    ZonedDateTime updateAt;

}