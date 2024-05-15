package smr.shop.libs.common.dto.message;

import lombok.*;
import smr.shop.libs.common.messaging.BaseMessageModel;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(of = "id")
public class CategoryMessageModel implements BaseMessageModel {
    private Long id;
}
