package smr.shop.coupon.service.dto.response;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * Author: Ali Gadashov
 * Version: v1.0
 * Date: 5/9/2024
 * Time: 3:00 PM
 */


@Value
@Builder
public class CouponResponse {

    UUID id;
    String code;
    String details;
    Long shopId;
    BigDecimal amount;
    short percentage;
    BigDecimal maxDiscountPrice;
    ZonedDateTime endDate;

}
