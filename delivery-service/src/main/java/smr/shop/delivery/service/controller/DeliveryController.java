package smr.shop.delivery.service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smr.shop.delivery.service.dto.request.DeliveryCreateRequest;
import smr.shop.delivery.service.dto.request.DeliveryUpdateRequest;
import smr.shop.delivery.service.dto.request.UpdateDeliveryStatusRequest;
import smr.shop.delivery.service.dto.response.DeliveryResponse;
import smr.shop.delivery.service.service.DeliveryService;
import smr.shop.libs.common.dto.response.EmptyResponse;

import java.util.List;

@RestController
@RequestMapping("/api/1.0/delivery")
public class DeliveryController {

    private final DeliveryService deliveryService;

    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

//    ----------------------------------- Post -----------------------------------

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<EmptyResponse> createDelivery(@RequestBody DeliveryCreateRequest request) {
        deliveryService.createDelivery(request);
        EmptyResponse response = EmptyResponse.builder()
                .message("Delivery created successfully")
                .build();
        return ResponseEntity.ok(response);
    }

//    ----------------------------------- Patch or Put -----------------------------------

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<EmptyResponse> updateDelivery(@PathVariable Long id, @RequestBody DeliveryUpdateRequest request) {
        deliveryService.updateDelivery(id, request);
        EmptyResponse response = EmptyResponse.builder()
                .message("Delivery updated successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<EmptyResponse> updateDeliveryStatus(@PathVariable Long id, @RequestBody UpdateDeliveryStatusRequest request) {
        deliveryService.updateDeliveryStatus(id, request);
        EmptyResponse response = EmptyResponse.builder()
                .message("Delivery Status updated successfully")
                .build();
        return ResponseEntity.ok(response);
    }

//    ----------------------------------- Delete -----------------------------------

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<EmptyResponse> deleteDelivery(@PathVariable Long id) {
        deliveryService.deleteDelivery(id);
        EmptyResponse response = EmptyResponse.builder()
                .message("Delivery deleted successfully with id: " + id)
                .build();
        return ResponseEntity.ok(response);
    }

//    ----------------------------------- Get -----------------------------------

    @GetMapping
    public ResponseEntity<List<DeliveryResponse>> getAllDelivery(Integer page) {
        List<DeliveryResponse> allDelivery = deliveryService.getAllDelivery(page);
        return ResponseEntity.ok(allDelivery);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeliveryResponse> getDeliveryById(@PathVariable Long id) {
        DeliveryResponse delivery = deliveryService.getDeliveryById(id);
        return ResponseEntity.ok(delivery);
    }

}
