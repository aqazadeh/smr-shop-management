package smr.shop.shop.service.outbox;


import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import smr.shop.libs.common.constant.MessagingConstants;
import smr.shop.libs.common.dto.message.ShopMessageModel;
import smr.shop.libs.common.dto.message.UploadMessageModel;
import smr.shop.libs.outbox.helper.OutboxHelper;
import smr.shop.libs.outbox.model.OutboxEntity;
import smr.shop.libs.outbox.service.OutboxService;
import smr.shop.shop.service.message.publisher.ImageDeleteMessagePublisher;
import smr.shop.shop.service.message.publisher.ShopStatusChangeMessagePublisher;

import java.util.List;

@Component
@EnableScheduling
public class OutboxProcessor {
    private final OutboxService outboxService;

    private final ImageDeleteMessagePublisher imageDeleteMessagePublisher;
    private final ShopStatusChangeMessagePublisher shopStatusChangeMessagePublisher;

    public OutboxProcessor(OutboxService outboxService,
                                               ImageDeleteMessagePublisher imageDeleteMessagePublisher,
                                               ShopStatusChangeMessagePublisher shopStatusChangeMessagePublisher) {
        this.outboxService = outboxService;
        this.imageDeleteMessagePublisher = imageDeleteMessagePublisher;
        this.shopStatusChangeMessagePublisher = shopStatusChangeMessagePublisher;
    }


    @Scheduled(fixedRate = 10000) // run every 10 second
    @Transactional
    public void processOutboxEvents() {
        List<String> events = List.of(
                MessagingConstants.IMAGE_DELETE_TOPIC,
                MessagingConstants.SHOP_STATUS_CHANGE_TOPIC);

        List<OutboxEntity> outboxEvents = outboxService.findAll(events);

        outboxEvents.forEach(outboxEntity -> {
            outboxService.remove(outboxEntity);
            switch (outboxEntity.getEvent()) {
                case MessagingConstants.IMAGE_DELETE_TOPIC -> {
                    UploadMessageModel uploadMessageModel = OutboxHelper.convertToObject(outboxEntity.getPayload(), UploadMessageModel.class);
                    imageDeleteMessagePublisher.publish(uploadMessageModel);
                }
                case MessagingConstants.SHOP_STATUS_CHANGE_TOPIC -> {
                    ShopMessageModel uploadMessageModel = OutboxHelper.convertToObject(outboxEntity.getPayload(), ShopMessageModel.class);
                    shopStatusChangeMessagePublisher.publish(uploadMessageModel);
                }
                default -> {
                    throw new RuntimeException("Unsupported outbox event: " + outboxEntity.getEvent());
                }
            }
        });
    }
}
