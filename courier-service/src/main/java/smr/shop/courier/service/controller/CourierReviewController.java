package smr.shop.courier.service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smr.shop.courier.service.dto.request.CreateCourierReviewRequest;
import smr.shop.courier.service.service.CourierReviewService;
import smr.shop.libs.common.dto.response.EmptyResponse;

@RestController
@RequestMapping("/api/v1/courier-review")
public class CourierReviewController {

    private final CourierReviewService courierReviewService;

    public CourierReviewController(CourierReviewService courierReviewService) {
        this.courierReviewService = courierReviewService;
    }

//    ----------------------------------- Post -----------------------------------

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<EmptyResponse> createCourierReview(@RequestBody CreateCourierReviewRequest request) {
        courierReviewService.createCourierReview(request);
        EmptyResponse response = EmptyResponse.builder()
                .message("Courier Review created successfully")
                .build();
        return ResponseEntity.ok(response);
    }

}
