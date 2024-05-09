package smr.shop.coupon.service.dto.response;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

/**
 * Author: Ali Gadashov
 * Version: v1.0
 * Date: 5/9/2024
 * Time: 3:39 PM
 */
@Value
@Builder
public class UserCouponResponse {

    UUID id;

    Long userId;
}
