package smr.shop.product.service.messaging;

import smr.shop.libs.common.messaging.publisher.MessagePublisher;
import smr.shop.product.service.dto.messaging.StockCreateMessageModel;

/**
 * Author: Ali Gadashov
 * Version: v1.0
 * Date: 5/11/2024
 * Time: 1:16 AM
 */

public interface ProductStockCreateMessagePublisher extends MessagePublisher<StockCreateMessageModel> {
}
