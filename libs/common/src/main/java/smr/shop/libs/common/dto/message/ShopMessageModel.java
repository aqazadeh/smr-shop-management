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
public class ShopMessageModel implements BaseMessageModel {

    private Long id;

    private UUID userId;

    private String shopName;

    private String slug;

    private String description;

    private String logo;

    private String phone;

//    private ShopAddress address;

//    private ShopStatus status;
}
