package smr.shop.delivery.service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smr.shop.delivery.service.dto.request.DeliveryCreateRequest;
import smr.shop.delivery.service.dto.request.DeliveryUpdateRequest;
import smr.shop.delivery.service.dto.request.UpdateDeliveryStatusRequest;
import smr.shop.delivery.service.dto.response.DeliveryResponse;
import smr.shop.delivery.service.service.DeliveryService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/delivery")
public class DeliveryController {

    private final DeliveryService deliveryService;

    public DeliveryController(DeliveryService deliveryService) {
        this.deliveryService = deliveryService;
    }

    @PostMapping
    public ResponseEntity<DeliveryResponse> createDelivery(@RequestBody DeliveryCreateRequest request) {
        DeliveryResponse deliveryResponse = deliveryService.createDelivery(request);
        return ResponseEntity.ok(deliveryResponse);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<DeliveryResponse> updateDelivery(@PathVariable Long id, @RequestBody DeliveryUpdateRequest request) {
        DeliveryResponse deliveryResponse = deliveryService.updateDelivery(id, request);
        return ResponseEntity.ok(deliveryResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDelivery(@PathVariable Long id) {
        deliveryService.deleteDelivery(id);
        return ResponseEntity.noContent().build();
    }

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

    @PutMapping("/{id}")
    public ResponseEntity<DeliveryResponse> updateDeliveryStatus(@PathVariable Long id, @RequestBody UpdateDeliveryStatusRequest request) {
        DeliveryResponse deliveryResponse = deliveryService.updateDeliveryStatus(id, request);
        return ResponseEntity.ok(deliveryResponse);
    }
}
