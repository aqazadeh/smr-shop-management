package smr.shop.libs.common.dto.message;

import lombok.*;
import smr.shop.libs.common.messaging.BaseMessageModel;

import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(of = "id")
public class CartMessageModel implements BaseMessageModel {
    private UUID id;

    private UUID userId;

    private String coupon;
}
