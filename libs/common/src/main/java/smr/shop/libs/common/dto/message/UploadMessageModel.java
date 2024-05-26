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
 * Time: 11:28 AM
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UploadMessageModel implements BaseMessageModel {
    String imageUrl;
}
