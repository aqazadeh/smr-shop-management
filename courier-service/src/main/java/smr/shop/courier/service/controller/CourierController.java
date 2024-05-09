package smr.shop.courier.service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smr.shop.courier.service.dto.request.CourierCreateRequest;
import smr.shop.courier.service.dto.request.CourierUpdateRequest;
import smr.shop.courier.service.dto.request.UpdateCourierActiveTypeRequest;
import smr.shop.courier.service.dto.response.CourierResponse;
import smr.shop.courier.service.service.CourierService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/courier")
public class CourierController {

    private final CourierService courierService;

    public CourierController(CourierService courierService) {
        this.courierService = courierService;
    }

    @PostMapping
    public ResponseEntity<CourierResponse> createCourier(@RequestBody  CourierCreateRequest request) {
        CourierResponse courierResponse = courierService.createCourier(request);
        return ResponseEntity.ok(courierResponse);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<CourierResponse> updateCourier(@PathVariable Long id, @RequestBody CourierUpdateRequest request) {
        CourierResponse courierResponse = courierService.updateCourier(id, request);
        return ResponseEntity.ok(courierResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCourier(@PathVariable Long id) {
        courierService.deleteCourier(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<CourierResponse>> getAllCourier(Integer page) {
        List<CourierResponse> allCourier = courierService.getAllCourier(page);
        return ResponseEntity.ok(allCourier);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CourierResponse> getCourierById(@PathVariable Long id) {
        CourierResponse courierResponse = courierService.getCourierById(id);
        return ResponseEntity.ok(courierResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CourierResponse> updateCourierActiveType(@PathVariable Long id, @RequestBody UpdateCourierActiveTypeRequest request) {
        CourierResponse courierResponse = courierService.updateCourierActiveType(id, request);
        return ResponseEntity.ok(courierResponse);
    }
}
