package smr.shop.flash.service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import smr.shop.flash.service.dto.request.FlashCreateRequest;
import smr.shop.flash.service.dto.request.FlashUpdateRequest;
import smr.shop.flash.service.dto.response.FlashItemResponse;
import smr.shop.flash.service.dto.response.FlashResponse;
import smr.shop.flash.service.service.FlashService;
import smr.shop.libs.common.dto.response.EmptyResponse;

import java.util.List;
import java.util.UUID;

/**
 * Author: Ali Gadashov
 * Version: v1.0
 * Date: 5/9/2024
 * Time: 7:30 PM
 */

@RestController
@RequestMapping("/api/1.0/flash/")
public class FlashController {

    private final FlashService flashService;

    public FlashController(FlashService flashService) {
        this.flashService = flashService;
    }

//    ----------------------------------- Post -----------------------------------

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<EmptyResponse> createFlash(@RequestBody FlashCreateRequest request) {
        flashService.createFlash(request);
        EmptyResponse response = EmptyResponse.builder()
                .message("Successfully created flash")
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{flashId}/{productId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<EmptyResponse> addProduct(@PathVariable Long flashId, @PathVariable Long productId) {
        flashService.addProduct(flashId, productId);
        EmptyResponse response = EmptyResponse.builder()
                .message("Successfully added product")
                .build();
        return ResponseEntity.ok(response);
    }

//    ----------------------------------- Put or Patch -----------------------------------

    @PutMapping("/{flashId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<EmptyResponse> updateFlash(@PathVariable Long flashId, @RequestBody FlashUpdateRequest request) {
        flashService.updateFlash(flashId, request);
        EmptyResponse response = EmptyResponse.builder()
                .message("Successfully updated flash")
                .build();
        return ResponseEntity.ok(response);
    }

//    ----------------------------------- Delete -----------------------------------

    @DeleteMapping("/{flashId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<EmptyResponse> deleteFlash(@PathVariable Long flashId) {
        flashService.deleteFlash(flashId);
        EmptyResponse response = EmptyResponse.builder()
                .message("Successfully deleted flash")
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{flashId}/item/{flashItemId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<EmptyResponse> deleteFlashItem(@PathVariable Long flashId, @PathVariable UUID flashItemId) {
        flashService.removeItem(flashId, flashItemId);
        EmptyResponse response = EmptyResponse.builder()
                .message("Successfully deleted flash item")
                .build();
        return ResponseEntity.ok(response);
    }

//    ----------------------------------- Get -----------------------------------

    @GetMapping
    public ResponseEntity<List<FlashResponse>> getAllFlashes(@RequestParam(value = "page", defaultValue = "0") Integer page) {
        List<FlashResponse> FlashResponselist = flashService.getAllFlashes(page);
        return ResponseEntity.ok(FlashResponselist);
    }


    @GetMapping("{flashId}")
    public ResponseEntity<List<FlashItemResponse>> getFlashItems(@PathVariable Long flashId, @RequestParam(value = "page", defaultValue = "0") Integer page) {
        List<FlashItemResponse> flashItemResponseList = flashService.getItems(flashId, page);
        return ResponseEntity.ok(flashItemResponseList);
    }

}
