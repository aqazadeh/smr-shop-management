package smr.shop.delivery.service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smr.shop.delivery.service.dto.request.CreateDeliveryConverstaionRequest;
import smr.shop.delivery.service.dto.request.UpdateDeliveryConversationRequest;
import smr.shop.delivery.service.dto.response.DeliveryConversationResponse;
import smr.shop.delivery.service.service.DeliveryConversationService;
import smr.shop.libs.common.dto.response.EmptyResponse;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/delivery-conversation")
public class DeliveryConversationController {

    private final DeliveryConversationService deliveryConversationService;

    public DeliveryConversationController(DeliveryConversationService deliveryConversationService) {
        this.deliveryConversationService = deliveryConversationService;
    }

//    ----------------------------------- Post -----------------------------------

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    ResponseEntity<EmptyResponse> createDeliveryConversation(@RequestBody CreateDeliveryConverstaionRequest request) {
        deliveryConversationService.createDeliveryConversation(request);
        EmptyResponse response = EmptyResponse.builder()
                .message("Delivery Conversation created successfully")
                .build();
        return ResponseEntity.ok(response);
    }

//    ----------------------------------- Patch or Put -----------------------------------

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<EmptyResponse> updateDeliveryConversation(@PathVariable UUID id, @RequestBody UpdateDeliveryConversationRequest request) {
        deliveryConversationService.updateDeliveryConversation(id, request);
        EmptyResponse response = EmptyResponse.builder()
                .message("Delivery Conversation updated successfully")
                .build();
        return ResponseEntity.ok(response);
    }
//    ----------------------------------- Delete -----------------------------------

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<EmptyResponse> deleteDeliveryConversation(@PathVariable UUID id) {
        deliveryConversationService.deleteDeliveryConversation(id);
        EmptyResponse response = EmptyResponse.builder()
                .message("Delivery Conversation deleted successfully")
                .build();
        return ResponseEntity.ok(response);
    }

//    ----------------------------------- Get -----------------------------------

    @GetMapping
    public ResponseEntity<List<DeliveryConversationResponse>> getAllDeliveryConversation(Integer page) {
        List<DeliveryConversationResponse> responses = deliveryConversationService.getAllDeliveryConversation(page);
        return ResponseEntity.ok(responses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeliveryConversationResponse> getDeliveryConversationById(@PathVariable UUID id) {
        DeliveryConversationResponse response = deliveryConversationService.getDeliveryConversationById(id);
        return ResponseEntity.ok(response);
    }
}
