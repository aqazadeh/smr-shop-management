package smr.shop.upload.service.messaging.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import smr.shop.libs.common.constant.MessagingConstants;
import smr.shop.libs.common.dto.message.BrandMessageModel;
import smr.shop.libs.common.messaging.listener.MessageListener;

@Component
@Slf4j
@RequiredArgsConstructor
public class BrandImageDeleteMessageListener implements MessageListener<BrandMessageModel> {

    @Override
    @KafkaListener(topics = {MessagingConstants.IMAGE_DELETE_TOPIC},
            groupId = MessagingConstants.IMAGE_DELETE_GROUP)
    public void receive(@Payload BrandMessageModel message,
                        @Header(KafkaHeaders.RECEIVED_KEY) String key,
                        @Header(KafkaHeaders.RECEIVED_PARTITION) Integer partition,
                        @Header(KafkaHeaders.OFFSET) Long offset) {

        log.info("{} number of brandMessageModel response received with key:{}, partition:{} and offset: {}",
                message.toString(),
                key,
                partition.toString(),
                offset.toString());

        // call service image delete method
    }


}
