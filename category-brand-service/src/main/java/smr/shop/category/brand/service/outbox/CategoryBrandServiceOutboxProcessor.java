package smr.shop.category.brand.service.outbox;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import smr.shop.category.brand.service.messaging.publisher.BrandDeleteMessagePublisher;
import smr.shop.category.brand.service.messaging.publisher.CategoryDeleteMessagePublisher;
import smr.shop.category.brand.service.messaging.publisher.ImageDeleteMessagePublisher;
import smr.shop.libs.common.constant.MessagingConstants;
import smr.shop.libs.common.dto.message.BrandMessageModel;
import smr.shop.libs.common.dto.message.CategoryMessageModel;
import smr.shop.libs.common.dto.message.UploadMessageModel;
import smr.shop.libs.outbox.helper.OutboxHelper;
import smr.shop.libs.outbox.model.OutboxEntity;
import smr.shop.libs.outbox.service.OutboxService;

import java.util.List;

@Component
@EnableScheduling
public class CategoryBrandServiceOutboxProcessor {
    private final OutboxService outboxService;

    private final ImageDeleteMessagePublisher imageDeleteMessagePublisher;
    private final BrandDeleteMessagePublisher brandDeleteMessagePublisher;
    private final CategoryDeleteMessagePublisher categoryDeleteMessagePublisher;

    public CategoryBrandServiceOutboxProcessor(OutboxService outboxService,
                                               ImageDeleteMessagePublisher imageDeleteMessagePublisher,
                                               BrandDeleteMessagePublisher brandDeleteMessagePublisher,
                                               CategoryDeleteMessagePublisher categoryDeleteMessagePublisher) {
        this.outboxService = outboxService;
        this.imageDeleteMessagePublisher = imageDeleteMessagePublisher;
        this.brandDeleteMessagePublisher = brandDeleteMessagePublisher;
        this.categoryDeleteMessagePublisher = categoryDeleteMessagePublisher;
    }

    @Scheduled(fixedRate = 10000) // run every 10 second
    @Transactional
    public void processOutboxEvents() {
        List<String> events = List.of(MessagingConstants.IMAGE_DELETE_TOPIC,
                MessagingConstants.BRAND_DELETE_TOPIC,
                MessagingConstants.CATEGORY_DELETE_TOPIC);

        List<OutboxEntity> outboxEvents = outboxService.findAll(events);

        outboxEvents.forEach(outboxEntity -> {
            outboxService.remove(outboxEntity);
            switch (outboxEntity.getEvent()){
                case MessagingConstants.IMAGE_DELETE_TOPIC -> {
                    UploadMessageModel uploadMessageModel = OutboxHelper.convertToObject(outboxEntity.getPayload(), UploadMessageModel.class);
                    imageDeleteMessagePublisher.publish(uploadMessageModel);
                }
                case MessagingConstants.BRAND_DELETE_TOPIC -> {
                    BrandMessageModel uploadMessageModel = OutboxHelper.convertToObject(outboxEntity.getPayload(), BrandMessageModel.class);
                    brandDeleteMessagePublisher.publish(uploadMessageModel);
                }
                case MessagingConstants.CATEGORY_DELETE_TOPIC -> {
                    CategoryMessageModel uploadMessageModel = OutboxHelper.convertToObject(outboxEntity.getPayload(), CategoryMessageModel.class);
                    categoryDeleteMessagePublisher.publish(uploadMessageModel);
                }
                default -> {
                    throw new RuntimeException("Unsupported outbox event: " + outboxEntity.getEvent());
                }
            }
        });
    }
}