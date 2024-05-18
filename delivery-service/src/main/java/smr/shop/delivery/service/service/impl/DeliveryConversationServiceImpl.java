package smr.shop.delivery.service.service.impl;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import smr.shop.delivery.service.dto.request.CreateDeliveryConverstaionRequest;
import smr.shop.delivery.service.dto.request.UpdateDeliveryConversationRequest;
import smr.shop.delivery.service.dto.response.DeliveryConversationResponse;
import smr.shop.delivery.service.exception.DeliveryConversationException;
import smr.shop.delivery.service.mapper.DeliveryConversationServiceMapper;
import smr.shop.delivery.service.model.DeliveryConversation;
import smr.shop.delivery.service.repository.DeliveryConversationRepository;
import smr.shop.delivery.service.service.DeliveryConversationService;
import smr.shop.libs.common.constant.ServiceConstants;

import java.awt.print.Pageable;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class DeliveryConversationServiceImpl implements DeliveryConversationService {

    private final DeliveryConversationRepository deliveryConversationRepository;
    private final DeliveryConversationServiceMapper conversationServiceMapper;

    public DeliveryConversationServiceImpl(DeliveryConversationRepository deliveryConversationRepository, DeliveryConversationServiceMapper conversationServiceMapper) {
        this.deliveryConversationRepository = deliveryConversationRepository;
        this.conversationServiceMapper = conversationServiceMapper;
    }

//    ----------------------------------- Create or Add -----------------------------------

    @Override
    @Transactional
    public void createDeliveryConversation(CreateDeliveryConverstaionRequest request) {
        DeliveryConversation deliveryConversation = conversationServiceMapper.toDeliveryConversation(request);
        deliveryConversation.setCreatedAt(ZonedDateTime.of(LocalDateTime.now(), ZoneId.of(ServiceConstants.UTC)));
        deliveryConversation.setUpdatedAt(ZonedDateTime.of(LocalDateTime.now(), ZoneId.of(ServiceConstants.UTC)));
        deliveryConversationRepository.save(deliveryConversation);
        conversationServiceMapper.toDeliveryConversationResponse(deliveryConversation);
    }

//    ----------------------------------- Update -----------------------------------

    @Override
    @Transactional
    public void updateDeliveryConversation(UUID id, UpdateDeliveryConversationRequest request) {
        DeliveryConversation conversation = findById(id);
        DeliveryConversation updateDeliveryConversation = conversationServiceMapper.toUpdateDeliveryConversation(request, conversation);
        conversation.setUpdatedAt(ZonedDateTime.of(LocalDateTime.now(), ZoneId.of(ServiceConstants.UTC)));
        deliveryConversationRepository.save(conversation);
    }

//    ----------------------------------- Delete ----------------------------------

    @Override
    @Transactional
    public void deleteDeliveryConversation(UUID id) {
        DeliveryConversation conversation = findById(id);
        conversation.setUpdatedAt(ZonedDateTime.of(LocalDateTime.now(), ZoneId.of(ServiceConstants.UTC)));
        deliveryConversationRepository.delete(conversation);
    }

//    ----------------------------------- Get -----------------------------------

    @Override
    public List<DeliveryConversationResponse> getAllDeliveryConversation(Integer page) {
        Pageable pageable = (Pageable) PageRequest.of(page, ServiceConstants.pageSize);
        return deliveryConversationRepository.findAll((org.springframework.data.domain.Pageable) pageable).map(conversationServiceMapper::toDeliveryConversationResponse).toList();
    }

    @Override
    public DeliveryConversationResponse getDeliveryConversationById(UUID id) {
        DeliveryConversation conversation = findById(id);
        return conversationServiceMapper.toDeliveryConversationResponse(conversation);
    }

//    ----------------------------------- Extra -----------------------------------

    @Override
    public DeliveryConversation findById(UUID id) {
        return deliveryConversationRepository.findById(id)
                .orElseThrow(() -> new DeliveryConversationException("Delivery Conversation Not Found With Id: " + id, HttpStatus.CREATED));
    }
}
