package smr.shop.delivery.service.mapper;

import org.springframework.stereotype.Component;
import smr.shop.delivery.service.dto.request.CreateDeliveryConverstaionRequest;
import smr.shop.delivery.service.dto.request.UpdateDeliveryConversationRequest;
import smr.shop.delivery.service.dto.response.DeliveryConversationResponse;
import smr.shop.delivery.service.model.DeliveryConversation;

@Component
public class DeliveryConversationServiceMapper {
    public DeliveryConversation toDeliveryConversation(CreateDeliveryConverstaionRequest request) {
        DeliveryConversation.DeliveryConversationBuilder builder = DeliveryConversation.builder();
        builder.userId(request.getUserId());
        //builder.delivery(request.getDeliveryId()); error
        builder.message(request.getMessage());
        return builder.build();
    }

    public DeliveryConversation toUpdateDeliveryConversation(UpdateDeliveryConversationRequest request, DeliveryConversation conversation) {
        if(request.getUserId() != null) conversation.setUserId(request.getUserId());
        // if(request.getDeliveryId() != null) conversation.setDelivery(request.getDeliveryId()); error
        if(request.getMessage() != null) conversation.setMessage(request.getMessage());
        return conversation;
    }

    public DeliveryConversationResponse toDeliveryConversationResponse(DeliveryConversation conversation) {
        return DeliveryConversationResponse.builder()
                .id(conversation.getId())
                .userId(conversation.getUserId())
                .deliveryId(conversation.getDelivery().getId())
                .message(conversation.getMessage())
                .build();
    }

}
