package smr.shop.libs.common.dto.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import smr.shop.libs.common.messaging.BaseMessageModel;

import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UseCouponMessageModel implements BaseMessageModel {

    private UUID couponId;

    private UUID userId;
}
