package smr.shop.delivery.service.service;

import smr.shop.delivery.service.dto.request.CreateDeliveryConverstaionRequest;
import smr.shop.delivery.service.dto.request.UpdateDeliveryConversationRequest;
import smr.shop.delivery.service.dto.response.DeliveryConversationResponse;
import smr.shop.delivery.service.model.DeliveryConversation;

import java.util.List;
import java.util.UUID;

public interface DeliveryConversationService {
    void createDeliveryConversation(CreateDeliveryConverstaionRequest request);

    void updateDeliveryConversation(UUID id, UpdateDeliveryConversationRequest request);

    void deleteDeliveryConversation(UUID id);

    List<DeliveryConversationResponse> getAllDeliveryConversation(Integer page);

    DeliveryConversationResponse getDeliveryConversationById(UUID id);

    DeliveryConversation findById(UUID id);
}
