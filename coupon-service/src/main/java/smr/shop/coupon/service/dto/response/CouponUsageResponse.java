package smr.shop.coupon.service.dto.response;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

/**
 * Author: Ali Gadashov
 * Version: v1.0
 * Date: 5/9/2024
 * Time: 3:38 PM
 */


@Value
@Builder
public class CouponUsageResponse {
    UUID id;

    Long userId;
}
