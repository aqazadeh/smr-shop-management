package smr.shop.courier.service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smr.shop.courier.service.dto.request.CourierCreateRequest;
import smr.shop.courier.service.dto.request.CourierUpdateRequest;
import smr.shop.courier.service.dto.request.UpdateCourierActiveTypeRequest;
import smr.shop.courier.service.dto.response.CourierResponse;
import smr.shop.courier.service.service.CourierService;
import smr.shop.libs.common.dto.response.EmptyResponse;

import java.util.List;

@RestController
@RequestMapping("/api/1.0/courier")
public class CourierController {

    private final CourierService courierService;

    public CourierController(CourierService courierService) {
        this.courierService = courierService;
    }

//    ----------------------------------- Post -----------------------------------

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<EmptyResponse> createCourier(@RequestBody CourierCreateRequest request) {
        courierService.createCourier(request);
        EmptyResponse response = EmptyResponse.builder()
                .message("Courier created successfully")
                .build();
        return ResponseEntity.ok(response);
    }

//    ----------------------------------- Patch or Put -----------------------------------

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<EmptyResponse> updateCourier(@PathVariable Long id, @RequestBody CourierUpdateRequest request) {
        courierService.updateCourier(id, request);
        EmptyResponse response = EmptyResponse.builder()
                .message("Courier updated successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<EmptyResponse> changeActiveType(@PathVariable Long id, @RequestBody UpdateCourierActiveTypeRequest request) {
        courierService.updateCourierActiveType(id, request);
        EmptyResponse response = EmptyResponse.builder()
                .message("Courier Active Type updated successfully")
                .build();
        return ResponseEntity.ok(response);
    }

//    ----------------------------------- Delete -----------------------------------

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<EmptyResponse> deleteCourier(@PathVariable Long id) {
        courierService.deleteCourier(id);
        EmptyResponse response = EmptyResponse.builder()
                .message("Courier deleted successfully")
                .build();
        return ResponseEntity.ok(response);
    }


//    ----------------------------------- Get -----------------------------------

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

}
