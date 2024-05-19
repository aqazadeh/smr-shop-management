package smr.shop.wishlist.service.messaging.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import smr.shop.libs.common.constant.MessagingConstants;
import smr.shop.libs.common.dto.message.ProductMessageModel;
import smr.shop.libs.common.messaging.listener.MessageListener;
import smr.shop.wishlist.service.service.WishlistService;

@Component
@Slf4j
public class ProductDeleteMessageListener implements MessageListener<ProductMessageModel> {

    private final WishlistService wishlistService;

    public ProductDeleteMessageListener(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @Override
    @KafkaListener(topics = {MessagingConstants.PRODUCT_DELETE_TOPIC}, groupId = MessagingConstants.WISHLIST_PRODUCT_DELETE_GROUP)
    public void receive(@Payload ProductMessageModel message,
                        @Header(KafkaHeaders.RECEIVED_KEY) String key,
                        @Header(KafkaHeaders.RECEIVED_PARTITION) Integer partition,
                        @Header(KafkaHeaders.OFFSET) Long offset) {
        log.info("{} number of ProductDeleteMessageModel response received with key: {}, partition: {} and offset: {}",
                message.toString(),
                key,
                partition.toString(),
                offset.toString());

        wishlistService.deleteProductsInWishlist(message);
    }
}
