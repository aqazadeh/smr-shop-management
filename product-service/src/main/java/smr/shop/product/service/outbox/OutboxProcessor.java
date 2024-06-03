package smr.shop.product.service.outbox;


import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import smr.shop.libs.common.constant.MessagingConstants;
import smr.shop.libs.common.dto.message.StockCreateMessageModel;
import smr.shop.libs.outbox.helper.OutboxHelper;
import smr.shop.libs.outbox.model.OutboxEntity;
import smr.shop.libs.outbox.service.OutboxService;
import smr.shop.product.service.messaging.publisher.ProductStockCreateMessagePublisher;

import java.util.List;

@Component
public class OutboxProcessor {
    private final OutboxService outboxService;
    private final ProductStockCreateMessagePublisher productStockCreateMessagePublisher;

    public OutboxProcessor(OutboxService outboxService, ProductStockCreateMessagePublisher productStockCreateMessagePublisher) {
        this.outboxService = outboxService;
        this.productStockCreateMessagePublisher = productStockCreateMessagePublisher;
    }

    @Scheduled(fixedRate = 5000)
    @Transactional
    public void processOutboxEvents() {
        List<String> events = List.of(MessagingConstants.CREATE_STOCK_TOPIC);

        List<OutboxEntity> outboxEvents = outboxService.findAll(events);

        outboxEvents.forEach(outboxEntity -> {
            outboxService.remove(outboxEntity);
            switch (outboxEntity.getEvent()) {
                case MessagingConstants.CREATE_STOCK_TOPIC -> {
                    StockCreateMessageModel stockCreateMessageModel = OutboxHelper.convertToObject(outboxEntity.getPayload(), StockCreateMessageModel.class);
                    productStockCreateMessagePublisher.publish(stockCreateMessageModel);
                }
                default -> {
                    throw new RuntimeException("Unsupported outbox event: " + outboxEntity.getEvent());
                }
            }
        });
    }
}

