package smr.shop.libs.common.dto.message;

import lombok.*;
import smr.shop.libs.common.messaging.BaseMessageModel;

import java.util.UUID;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PaymentMessageModel implements BaseMessageModel {
    private UUID id;
}
