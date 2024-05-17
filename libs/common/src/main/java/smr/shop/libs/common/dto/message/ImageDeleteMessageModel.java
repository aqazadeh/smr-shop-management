package smr.shop.libs.common.dto.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import smr.shop.libs.common.messaging.BaseMessageModel;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class ImageDeleteMessageModel implements BaseMessageModel {
    private String imageUrl;
}
