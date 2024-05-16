package smr.shop.product.service.messaging.publisher;

import org.springframework.stereotype.Component;
import smr.shop.product.service.dto.messaging.StockCreateMessageModel;
import smr.shop.product.service.messaging.ProductStockCreateMessagePublisher;

@Component
public class ProductStockCreateMessagePublisherImpl implements ProductStockCreateMessagePublisher {
    @Override
    public void publish(StockCreateMessageModel messageModel) {

    }
}
