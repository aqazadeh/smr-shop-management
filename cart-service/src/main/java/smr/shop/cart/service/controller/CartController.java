package smr.shop.cart.service.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smr.shop.cart.service.dto.response.CartResponse;
import smr.shop.cart.service.service.CartService;
import smr.shop.libs.common.dto.response.EmptyResponse;

import java.util.UUID;

/**
 * Author: Ali Gadashov
 * Version: v1.0
 * Date: 5/10/2024
 * Time: 2:45 PM
 */

@RestController
@RequestMapping("/api/1.0/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }


//    ----------------------------------- Post -----------------------------------

    @PostMapping("/{productId}/{stockId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<EmptyResponse> addToCart(@PathVariable Long productId, @PathVariable UUID stockId) {
        cartService.addProductToCart(productId, stockId);
        EmptyResponse response = EmptyResponse.builder().message("Product add to cart successfully").build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/coupon/{couponCode}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CartResponse> addCouponToCart(@PathVariable String couponCode) {
        cartService.addCoupon(couponCode);
        return null;
    }

    @PostMapping("/{cartItemId}/increase")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CartResponse> increaseCartItem(@PathVariable UUID cartItemId) {
        cartService.increase(cartItemId);
        return null;
    }

//    ----------------------------------- Get -----------------------------------

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CartResponse> getUserCart() {
        CartResponse cartResponse = cartService.getUserCart();
        return ResponseEntity.ok(cartResponse);
    }

//    ----------------------------------- Delete -----------------------------------

    @DeleteMapping("/{cartItemId}/remove")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CartResponse> removeItemFromCart(@PathVariable UUID cartItemId) {
        cartService.deleteCartItem(cartItemId);
        return null;
    }

    @DeleteMapping("/{cartItemId}/decrease")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CartResponse> decreaseCartItem(@PathVariable UUID cartItemId) {
        cartService.decrease(cartItemId);
        return null;
    }

    @DeleteMapping("/coupon/remove")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<CartResponse> removeCouponFromCart() {
        cartService.removeCoupon();
        return null;
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<EmptyResponse> clearCart() {
        cartService.clearCart();
        EmptyResponse response = EmptyResponse.builder().message("Cart clear successfully").build();
        return ResponseEntity.ok().body(response);
    }

}
