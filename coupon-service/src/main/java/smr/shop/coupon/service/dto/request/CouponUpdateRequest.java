package smr.shop.coupon.service.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Value;

import java.math.BigDecimal;
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
    String code;

    @NotBlank
    String details;

    BigDecimal amount;

    Short percentage;

    @NotNull
    BigDecimal maxDiscountPrice;

    @NotNull
    ZonedDateTime endDate;

}
