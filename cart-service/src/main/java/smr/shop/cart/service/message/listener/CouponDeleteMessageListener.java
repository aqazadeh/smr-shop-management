package smr.shop.cart.service.message.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import smr.shop.cart.service.service.CartService;
import smr.shop.libs.common.constant.MessagingConstants;
import smr.shop.libs.common.dto.message.CouponMessageModel;
import smr.shop.libs.common.messaging.listener.MessageListener;

@Component
@Slf4j
public class CouponDeleteMessageListener implements MessageListener<CouponMessageModel> {

    private final CartService cartService;

    public CouponDeleteMessageListener(CartService cartService) {
        this.cartService = cartService;
    }

    @Override
    @KafkaListener(topics = MessagingConstants.PRODUCT_DELETE_TOPIC, groupId = MessagingConstants.CART_COUPON_DELETE_GROUP)
    public void receive(@Payload CouponMessageModel message,
                        @Header(KafkaHeaders.RECEIVED_KEY) String key,
                        @Header(KafkaHeaders.RECEIVED_PARTITION) Integer partition,
                        @Header(KafkaHeaders.OFFSET) Long offset) {

        cartService.removeCouponInItems(message);
    }
}
