package smr.shop.product.service.messaging.publisher;

import org.springframework.stereotype.Component;
import smr.shop.libs.common.messaging.publisher.MessagePublisher;
import smr.shop.libs.common.dto.message.StockCreateMessageModel;

@Component
public class ProductStockCreateMessagePublisher implements MessagePublisher<StockCreateMessageModel> {

    @Override
    public void publish(StockCreateMessageModel messageModel) {

    }
}
