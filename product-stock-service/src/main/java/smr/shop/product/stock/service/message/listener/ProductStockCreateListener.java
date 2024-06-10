package smr.shop.product.stock.service.message.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import smr.shop.libs.common.constant.MessagingConstants;
import smr.shop.libs.common.dto.message.ProductStockMessageModel;
import smr.shop.libs.common.dto.message.StockCreateMessageModel;
import smr.shop.libs.common.messaging.listener.MessageListener;
import smr.shop.product.stock.service.service.ProductStockService;

import java.util.List;

@Component
@Slf4j
public class ProductStockCreateListener implements MessageListener<StockCreateMessageModel> {
    private final ProductStockService productStockService;

    public ProductStockCreateListener(ProductStockService productStockService) {
        this.productStockService = productStockService;
    }

    @Override
    @KafkaListener(topics = MessagingConstants.CREATE_STOCK_TOPIC, groupId = "stock-service-group")
    public void receive(@Payload StockCreateMessageModel message,
                        @Header(KafkaHeaders.RECEIVED_KEY) String key,
                        @Header(KafkaHeaders.RECEIVED_PARTITION) Integer partition,
                        @Header(KafkaHeaders.OFFSET) Long offset) {

        log.info("{} number of ProductStockMessageModel response received with key:{}, partition:{} and offset: {}",
                message.toString(),
                key,
                partition.toString(),
                offset.toString());

        productStockService.createAll(message.getStocks());
    }
}
