package smr.shop.discount.service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smr.shop.discount.service.dto.request.DiscountCreateRequest;
import smr.shop.discount.service.dto.response.DiscountResponse;
import smr.shop.discount.service.service.DiscountService;
import smr.shop.libs.common.dto.response.EmptyResponse;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/1.0/product")
public class DiscountController {
    private final DiscountService discountService;

    public DiscountController(DiscountService discountService) {
        this.discountService = discountService;
    }

//    ----------------------------------- Post -----------------------------------

    @PostMapping("/{productId}/discount")
    public ResponseEntity<EmptyResponse> createDiscount(@PathVariable Long productId, @RequestBody DiscountCreateRequest request) {
        discountService.createDiscount(productId, request);
        EmptyResponse emptyResponse = EmptyResponse.builder().message("Discount created successfully").build();
        return ResponseEntity.ok(emptyResponse);
    }

//    ----------------------------------- Delete -----------------------------------

    @DeleteMapping("/discount/{id}")
    public ResponseEntity<EmptyResponse> deleteDiscount(@PathVariable UUID id) {
        discountService.deleteDiscount(id);
        EmptyResponse emptyResponse = EmptyResponse.builder().message("Discount deleted successfully").build();
        return ResponseEntity.ok(emptyResponse);
    }

//    ----------------------------------- Get -----------------------------------

    @GetMapping("/discount/{id}")
    public ResponseEntity<DiscountResponse> getDiscountById(@PathVariable UUID id) {
        DiscountResponse discountResponse = discountService.getDiscountById(id);
        return ResponseEntity.ok(discountResponse);
    }

    @GetMapping("/discount/shop/{shopId}")
    public ResponseEntity<List<DiscountResponse>> getShopDiscounts(@PathVariable Long shopId,
                                                                   @RequestParam(value = "page", defaultValue = "0") Integer page) {
        List<DiscountResponse> discountResponse = discountService.getShopDiscounts(shopId, page);
        return ResponseEntity.ok(discountResponse);
    }

}