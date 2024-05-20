package smr.shop.libs.common.dto.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import smr.shop.libs.common.messaging.BaseMessageModel;

/**
 * Author: Ali Gadashov
 * Version: v1.0
 * Date: 5/11/2024
 * Time: 1:45 AM
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class StockCreateMessageModel implements BaseMessageModel {

    String attributeName;

    Integer quantity;

}
