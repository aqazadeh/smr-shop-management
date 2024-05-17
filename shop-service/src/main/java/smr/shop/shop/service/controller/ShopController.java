package smr.shop.shop.service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smr.shop.libs.common.dto.response.EmptyResponse;
import smr.shop.shop.service.dto.request.CreateShopRequest;
import smr.shop.shop.service.dto.request.UpdateShopAddressRequest;
import smr.shop.shop.service.dto.request.UpdateShopRequest;
import smr.shop.shop.service.dto.response.ShopAddressResponse;
import smr.shop.shop.service.dto.response.ShopResponse;
import smr.shop.shop.service.model.valueobject.ShopStatus;
import smr.shop.shop.service.service.ShopService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/shop")
public class ShopController {

    private final ShopService shopService;

    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @PostMapping
    public ResponseEntity<EmptyResponse> createShop(@RequestBody CreateShopRequest request) {
        shopService.createShop(request);
        EmptyResponse response = EmptyResponse.builder()
                .message("Shop created successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{shopId}")
    public ResponseEntity<EmptyResponse> updateShop(@PathVariable Long shopId, @RequestBody UpdateShopRequest request) {
        shopService.updateShop(shopId, request);
        EmptyResponse response = EmptyResponse.builder()
                .message("Shop updated successfully with shopId: " + shopId)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{shopId}")
    public ResponseEntity<EmptyResponse> deleteShop(@PathVariable Long shopId) {
        shopService.deleteShop(shopId);
        EmptyResponse response = EmptyResponse.builder()
                .message("Shop deleted successfully with shopId: " + shopId)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ShopResponse>> getAllShop(@RequestParam(value = "page", defaultValue = "0") Integer page) {
        List<ShopResponse> shopResponses = shopService.getAllShop(page);
        return ResponseEntity.ok(shopResponses);
    }

    @GetMapping("/{shopId}")
    public ResponseEntity<ShopResponse> getShopById(@PathVariable Long shopId) {
        ShopResponse shopResponse = shopService.getShopById(shopId);
        return ResponseEntity.ok(shopResponse);
    }

    @PutMapping("/{shopId}")
    public ResponseEntity<EmptyResponse> updateShopStatus(@PathVariable Long shopId, @RequestParam ShopStatus status) {
        shopService.updateShopStatus(shopId, status);
        EmptyResponse response = EmptyResponse.builder()
                .message("Shop status successfully updated with shopId: " + shopId)
                .build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{shopId}/address")
    public ResponseEntity<EmptyResponse> updateShopAddress(@PathVariable Long shopId, @RequestBody UpdateShopAddressRequest request) {
        shopService.updateShopAddress(shopId, request);
        EmptyResponse response = EmptyResponse.builder()
                .message("Shop address successfully with shopId: " + shopId)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{shopId}/address")
    public ResponseEntity<ShopAddressResponse> getShopAddress(@PathVariable Long shopId) {
        ShopAddressResponse shopAddressResponse = shopService.getShopAddress(shopId);
        return ResponseEntity.ok(shopAddressResponse);
    }
}
