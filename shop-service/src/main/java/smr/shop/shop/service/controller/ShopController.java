package smr.shop.shop.service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smr.shop.shop.service.dto.request.*;
import smr.shop.shop.service.dto.response.ShopAddressResponse;
import smr.shop.shop.service.dto.response.ShopResponse;
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
    public ResponseEntity<ShopResponse> createShop(@RequestBody CreateShopRequest request) {
        ShopResponse shopResponse = shopService.createShop(request);
        return ResponseEntity.ok(shopResponse);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ShopResponse> updateShop(@PathVariable Long id, @RequestBody UpdateShopRequest request) {
        ShopResponse shopResponse = shopService.updateShop(id, request);
        return ResponseEntity.ok(shopResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShop(@PathVariable Long id) {
        shopService.deleteShop(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<ShopResponse>> getAllShop(Integer page) {
        List<ShopResponse> shopResponses = shopService.getAllShop(page);
        return ResponseEntity.ok(shopResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShopResponse> getShopById(@PathVariable Long id) {
        ShopResponse shopResponse = shopService.getShopById(id);
        return ResponseEntity.ok(shopResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ShopResponse> updateShopStatus(@PathVariable Long id, @RequestBody UpdateShopStatusRequest request) {
        ShopResponse shopResponse = shopService.updateShopStatus(id, request);
        return ResponseEntity.ok(shopResponse);
    }

    @PatchMapping("/{id}/address/}")
    public ResponseEntity<ShopAddressResponse> updateShopAddress(@PathVariable Long id, @RequestBody UpdateShopAddressRequest request) {
        ShopAddressResponse shopAddressResponse = shopService.updateShopAddress(id, request);
        return ResponseEntity.ok(shopAddressResponse);
    }

    @GetMapping("/{id}/adress/{addressId}")
    public ResponseEntity<ShopAddressResponse> getShopAddressById(@PathVariable Long id, @PathVariable Long addressId) {
        ShopAddressResponse shopAddressResponse = shopService.getShopAddressById(id, addressId);
        return ResponseEntity.ok(shopAddressResponse);
    }
}
