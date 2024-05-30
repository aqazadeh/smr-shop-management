package smr.shop.product.review.service.outbox;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import smr.shop.libs.common.constant.MessagingConstants;
import smr.shop.libs.common.dto.message.ProductReviewMessageModel;
import smr.shop.libs.outbox.helper.OutboxHelper;
import smr.shop.libs.outbox.model.OutboxEntity;
import smr.shop.libs.outbox.service.OutboxService;
import smr.shop.product.review.service.message.publisher.ProductRatingChangeMessagePublisher;
import smr.shop.product.review.service.message.publisher.ProductReviewUpdateMessagePublisher;

import java.util.List;

@Component
public class OutboxProcessor {
    private final OutboxService outboxService;
    private final ProductRatingChangeMessagePublisher productRatingChangeMessagePublisher;
    private final ProductReviewUpdateMessagePublisher productReviewUpdateMessagePublisher;

    public OutboxProcessor(OutboxService outboxService,
                           ProductRatingChangeMessagePublisher productRatingChangeMessagePublisher,
                           ProductReviewUpdateMessagePublisher productReviewUpdateMessagePublisher) {
        this.outboxService = outboxService;
        this.productRatingChangeMessagePublisher = productRatingChangeMessagePublisher;
        this.productReviewUpdateMessagePublisher = productReviewUpdateMessagePublisher;
    }

    @Scheduled(fixedRate = 5000)
    @Transactional
    public void processOutboxEvents() {
        List<String> events = List.of(MessagingConstants.PRODUCT_REVIEW_CHANGE_TOPIC, MessagingConstants.PRODUCT_REVIEW_UPDATE_TOPIC);

        List<OutboxEntity> outboxEvents = outboxService.findAll(events);

        outboxEvents.forEach(outboxEntity -> {
            outboxService.remove(outboxEntity);
            switch (outboxEntity.getEvent()) {
                case MessagingConstants.PRODUCT_REVIEW_CHANGE_TOPIC -> {
                    ProductReviewMessageModel productReviewMessageModel = OutboxHelper.convertToObject(outboxEntity.getPayload(), ProductReviewMessageModel.class);
                    productRatingChangeMessagePublisher.publish(productReviewMessageModel);
                }
                case MessagingConstants.PRODUCT_REVIEW_UPDATE_TOPIC -> {
                    ProductReviewMessageModel productReviewMessageModel = OutboxHelper.convertToObject(outboxEntity.getPayload(), ProductReviewMessageModel.class);
                    productReviewUpdateMessagePublisher.publish(productReviewMessageModel);
                }
                default -> {
                    throw new RuntimeException("Unsupported outbox event: " + outboxEntity.getEvent());
                }
            }
        });
    }
}
