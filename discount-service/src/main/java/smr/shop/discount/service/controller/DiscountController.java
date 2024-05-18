package smr.shop.discount.service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smr.shop.discount.service.dto.request.DiscountCreateRequest;
import smr.shop.discount.service.dto.request.DiscountUpdateRequest;
import smr.shop.discount.service.dto.response.DiscountResponse;
import smr.shop.discount.service.service.DiscountService;
import smr.shop.libs.common.dto.response.EmptyResponse;

@RestController
@RequestMapping("/api/v1/discount")
public class DiscountController {
    private final DiscountService discountService;

    public DiscountController(DiscountService discountService) {
        this.discountService = discountService;
    }

//    ----------------------------------- Post -----------------------------------

    @PostMapping
    public ResponseEntity<EmptyResponse> createDiscount(@RequestBody DiscountCreateRequest request){
        discountService.createDiscount(request);
        EmptyResponse emptyResponse=EmptyResponse.builder().message("Discount created successfully").build();
        return ResponseEntity.ok(emptyResponse);
    }

//    ----------------------------------- Patch or Put -----------------------------------

    @PatchMapping("/{id}")
    public ResponseEntity<EmptyResponse> updateDiscount(@PathVariable Long id, @RequestBody DiscountUpdateRequest request){
        discountService.updateDiscount(id, request);
        EmptyResponse emptyResponse=EmptyResponse.builder().message("Discount updated succeefully").build();
        return ResponseEntity.ok(emptyResponse);
    }

//    ----------------------------------- Delete -----------------------------------

    @DeleteMapping("/{id}")
    public ResponseEntity<EmptyResponse>deleteDiscount(@PathVariable Long id){
        discountService.deleteDiscount(id);
        EmptyResponse emptyResponse=EmptyResponse.builder().message("Discount deleted successfully").build();
        return ResponseEntity.ok(emptyResponse);
    }

//    ----------------------------------- Get -----------------------------------

    @GetMapping("/{id}")
    public ResponseEntity<DiscountResponse>getDiscountById(@PathVariable Long id){
        DiscountResponse discountResponse = discountService.getDiscountById(id);
        return ResponseEntity.ok(discountResponse);
    }

}