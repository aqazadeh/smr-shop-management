package smr.shop.flash.service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smr.shop.flash.service.dto.request.FlashDealProductCreateRequest;
import smr.shop.flash.service.dto.request.FlashDealProductUpdateRequest;
import smr.shop.flash.service.dto.response.FlashDealProductResponse;
import smr.shop.flash.service.service.FlashDealProductService;

import java.util.List;
import java.util.UUID;

/**
 * Author: Ali Gadashov
 * Version: v1.0
 * Date: 5/9/2024
 * Time: 8:04 PM
 */


@RestController
@RequestMapping("/api/1.0/flash-deal")
public class FlashDealProductController {

    private final FlashDealProductService flashDealProductService;

    public FlashDealProductController(FlashDealProductService flashDealProductService) {
        this.flashDealProductService = flashDealProductService;
    }


//    ----------------------------------- Post -----------------------------------

    @PostMapping
    public ResponseEntity<FlashDealProductResponse> createFlashDealProduct(@RequestBody FlashDealProductCreateRequest request) {
        FlashDealProductResponse flashDealProductResponse = flashDealProductService.createFlashDealProduct(request);
        return ResponseEntity.ok(flashDealProductResponse);
    }

//    ----------------------------------- Put or Patch -----------------------------------

    @PatchMapping("/{flashDealProductId}")
    public ResponseEntity<FlashDealProductResponse> updateFlashDealProduct(@PathVariable UUID flashDealProductId, @RequestBody FlashDealProductUpdateRequest request) {
        FlashDealProductResponse flashDealProductResponse = flashDealProductService.updateFlashDealProduct(flashDealProductId, request);
        return ResponseEntity.ok(flashDealProductResponse);
    }

//    ----------------------------------- Delete -----------------------------------

    @DeleteMapping("/{flashDealProductId}")
    public ResponseEntity<Void> deleteFlashDealProduct(@PathVariable UUID flashDealProductId) {
        flashDealProductService.deleteFlashDealProduct(flashDealProductId);
        return ResponseEntity.noContent().build();
    }

    //    ----------------------------------- Get -----------------------------------

    @GetMapping
    public ResponseEntity<List<FlashDealProductResponse>> getAllFlashDealProduct(Integer page) {
        List<FlashDealProductResponse> allFlashDealProduct = flashDealProductService.getAllFlashDealProducts(page);
        return ResponseEntity.ok(allFlashDealProduct);
    }

    @GetMapping("/{flashDealProductId}")
    public ResponseEntity<FlashDealProductResponse> getFlashDealProductById(@PathVariable UUID flashDealProductId) {
        FlashDealProductResponse flashDealProductResponse = flashDealProductService.getFlashDealProductById(flashDealProductId);
        return ResponseEntity.ok(flashDealProductResponse);
    }

}
