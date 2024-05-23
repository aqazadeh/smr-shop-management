package smr.shop.product.service.messaging.listener;

import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import smr.shop.libs.common.dto.message.RatingMessageModel;
import smr.shop.libs.common.messaging.listener.MessageListener;
import smr.shop.product.service.service.ProductService;

@Component
public class ProductRatingChangeMessageListener implements MessageListener<RatingMessageModel> {
    private final ProductService productService;

    public ProductRatingChangeMessageListener(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public void receive(@Payload RatingMessageModel message,
                        @Header(KafkaHeaders.RECEIVED_KEY) String key,
                        @Header(KafkaHeaders.RECEIVED_PARTITION) Integer partition,
                        @Header(KafkaHeaders.OFFSET) Long offset) {
        productService.updateProductRating(message);
    }
}
