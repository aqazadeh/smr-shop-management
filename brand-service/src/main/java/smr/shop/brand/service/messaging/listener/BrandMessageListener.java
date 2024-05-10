package smr.shop.brand.service.messaging.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import smr.shop.libs.common.dto.message.BrandDeleteMessageModel;
import smr.shop.libs.common.messaging.listener.MessageListener;

@Component
@Slf4j
public class BrandMessageListener implements MessageListener<BrandDeleteMessageModel> {

    @Override
    @KafkaListener(topics = "brand-event-topic", groupId = "brand-event-group-1")
    public void receive(@Payload BrandDeleteMessageModel message,
                        @Header(KafkaHeaders.RECEIVED_KEY) String key,
                        @Header(KafkaHeaders.RECEIVED_PARTITION) Integer partition,
                        @Header(KafkaHeaders.OFFSET) Long offset) {
        log.info("{} number of payment responses received with keys:{}, partitions:{} and offsets: {}",
                message.toString(),
                key,
                partition.toString(),
                offset.toString());

        //call service and run operations
    }


}
