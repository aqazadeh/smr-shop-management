package smr.shop.wishlist.service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smr.shop.libs.common.dto.response.EmptyResponse;
import smr.shop.wishlist.service.dto.response.WishlistResponse;
import smr.shop.wishlist.service.service.WishlistService;

import java.util.List;

@RestController
@RequestMapping("/api/1.0/wishlist")
public class WishlistController {
    private final WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }

    @PostMapping("/{productId}/add")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<EmptyResponse> addProductToWishlist(@PathVariable Long productId) {
        wishlistService.addProductToWishlist(productId);
        EmptyResponse response = EmptyResponse.builder().message("successfully added product to wishlist with productId: " + productId).build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{productId}/remove")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<EmptyResponse> removeProductToWishlist(@PathVariable Long productId) {
        wishlistService.deleteProductInUserWishlist(productId);
        EmptyResponse response = EmptyResponse.builder().message("successfully removed product to wishlist with productId: " + productId).build();
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<List<WishlistResponse>> getWishlist(@RequestParam Integer page) {
        List<WishlistResponse> wishlistProducts = wishlistService.getAllWishlistProducts(page);
        return ResponseEntity.ok(wishlistProducts);
    }
}
