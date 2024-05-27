package smr.shop.coupon.service.outbox;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import smr.shop.coupon.service.message.publisher.CouponDeleteMessagePublisher;
import smr.shop.libs.common.constant.MessagingConstants;
import smr.shop.libs.common.dto.message.BrandMessageModel;
import smr.shop.libs.common.dto.message.CategoryMessageModel;
import smr.shop.libs.common.dto.message.CouponMessageModel;
import smr.shop.libs.common.dto.message.UploadMessageModel;
import smr.shop.libs.outbox.helper.OutboxHelper;
import smr.shop.libs.outbox.model.OutboxEntity;
import smr.shop.libs.outbox.service.OutboxService;

import java.util.List;

@Component
@EnableScheduling
public class CouponServiceOutboxProcessor {
    private final OutboxService outboxService;
    private final CouponDeleteMessagePublisher couponDeleteMessagePublisher;

    public CouponServiceOutboxProcessor(OutboxService outboxService,
                                        CouponDeleteMessagePublisher couponDeleteMessagePublisher) {
        this.outboxService = outboxService;
        this.couponDeleteMessagePublisher = couponDeleteMessagePublisher;
    }

    @Scheduled(fixedRate = 10000) // run every 10 second
    @Transactional
    public void processOutboxEvents() {
        List<OutboxEntity> outboxEvents = outboxService.findAll();
        outboxEvents.forEach(outboxEntity -> {
            outboxService.remove(outboxEntity);
            switch (outboxEntity.getEvent()){
                case MessagingConstants.COUPON_DELETE_TOPIC -> {
                    CouponMessageModel uploadMessageModel = OutboxHelper.convertToObject(outboxEntity.getPayload(), CouponMessageModel.class);
                    couponDeleteMessagePublisher.publish(uploadMessageModel);
                }
                default -> {
                    throw new RuntimeException("unknown event " + outboxEntity.getEvent());
                }
            }
        });
    }
}