package smr.shop.flash.service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smr.shop.flash.service.dto.request.FlashDealCreateRequest;
import smr.shop.flash.service.dto.request.FlashDealUpdateRequest;
import smr.shop.flash.service.dto.response.FlashDealResponse;
import smr.shop.flash.service.service.FlashDealService;

import java.util.List;

/**
 * Author: Ali Gadashov
 * Version: v1.0
 * Date: 5/9/2024
 * Time: 7:30 PM
 */

@RestController
@RequestMapping("/api/v1/flash-deal")
public class FlashDealController {

    private final FlashDealService flashDealService;

    public FlashDealController(FlashDealService flashDealService) {
        this.flashDealService = flashDealService;
    }

    @PostMapping
    public ResponseEntity<FlashDealResponse> createFlashDeal(@RequestBody FlashDealCreateRequest request) {
        FlashDealResponse flashDealResponse = flashDealService.createFlashDeal(request);
        return ResponseEntity.ok(flashDealResponse);
    }

    @PatchMapping("/{flashDealId}")
    public ResponseEntity<FlashDealResponse> updateFlashDeal(@PathVariable Long flashDealId, @RequestBody FlashDealUpdateRequest request) {
        FlashDealResponse flashDealResponse = flashDealService.updateFlashDeal(flashDealId, request);
        return ResponseEntity.ok(flashDealResponse);
    }

    @DeleteMapping("/{flashDealId}")
    public ResponseEntity<Void> deleteFlashDeal(@PathVariable Long flashDealId) {
        flashDealService.deleteFlashDeal(flashDealId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<FlashDealResponse>> getAllFlashDeal(Integer page) {
        List<FlashDealResponse> allFlashDeal = flashDealService.getAllFlashDeals(page);
        return ResponseEntity.ok(allFlashDeal);
    }

    @GetMapping("/{flashDealId}")
    public ResponseEntity<FlashDealResponse> getFlashDealById(@PathVariable Long flashDealId) {
        FlashDealResponse flashDealResponse = flashDealService.getFlashDealById(flashDealId);
        return ResponseEntity.ok(flashDealResponse);
    }
    
}
