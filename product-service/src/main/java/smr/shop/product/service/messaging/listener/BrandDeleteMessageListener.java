package smr.shop.product.service.messaging.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import smr.shop.libs.common.constant.MessagingConstants;
import smr.shop.libs.common.dto.message.BrandDeleteMessageModel;
import smr.shop.libs.common.messaging.listener.MessageListener;
import smr.shop.product.service.service.ProductService;

@Component
@Slf4j
public class BrandDeleteMessageListener implements MessageListener<BrandDeleteMessageModel> {


    private final ProductService productService;

    public BrandDeleteMessageListener(ProductService productService) {
        this.productService = productService;
    }


    @Override
    @KafkaListener(topics = {MessagingConstants.BRAND_DELETE_TOPIC},
            groupId = MessagingConstants.PRODUCT_BRAND_DELETE_GROUP)
    public void receive(@Payload BrandDeleteMessageModel message,
                        @Header(KafkaHeaders.RECEIVED_KEY) String key,
                        @Header(KafkaHeaders.RECEIVED_PARTITION) Integer partition,
                        @Header(KafkaHeaders.OFFSET) Long offset) {

        log.info("{} number of brandMessageModel response received with key:{}, partition:{} and offset: {}",
                message.toString(),
                key,
                partition.toString(),
                offset.toString());

        productService.deleteProductsByBrand(message.getId());

        // call service image delete method
    }




}
