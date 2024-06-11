package smr.shop.upload.service.messaging.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;
import smr.shop.libs.common.constant.MessagingConstants;
import smr.shop.libs.common.dto.message.UploadMessageModel;
import smr.shop.libs.common.messaging.listener.MessageListener;
import smr.shop.upload.service.service.UploadService;

@Component
@Slf4j
public class ImageDeleteMessageListener implements MessageListener<UploadMessageModel> {

    private final UploadService uploadService;

    public ImageDeleteMessageListener(UploadService uploadService) {
        this.uploadService = uploadService;
    }

    @Override
    @KafkaListener(topics = MessagingConstants.IMAGE_DELETE_TOPIC, groupId = MessagingConstants.UPLOAD_SERVICE_GROUP)
    public void receive(@Payload UploadMessageModel message,
                        @Header(KafkaHeaders.RECEIVED_KEY) String key,
                        @Header(KafkaHeaders.RECEIVED_PARTITION) Integer partition,
                        @Header(KafkaHeaders.OFFSET) Long offset) {

        log.info("{} number of UploadMessageModel response received with key:{}, partition:{} and offset: {}",
                message.toString(),
                key,
                partition.toString(),
                offset.toString());

        uploadService.delete(message);
        // call service image delete method
    }


}
