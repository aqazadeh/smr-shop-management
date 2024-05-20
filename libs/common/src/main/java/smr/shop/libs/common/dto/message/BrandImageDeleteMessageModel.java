package smr.shop.libs.common.dto.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import smr.shop.libs.common.messaging.BaseMessageModel;

/**
 * Author: Ali Gadashov
 * Version: v1.0
 * Date: 5/20/2024
 * Time: 11:31 AM
 */

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BrandImageDeleteMessageModel implements BaseMessageModel {

    private String imageId;
}