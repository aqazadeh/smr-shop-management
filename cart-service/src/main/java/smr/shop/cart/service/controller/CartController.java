package smr.shop.cart.service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smr.shop.cart.service.dto.response.CartResponse;
import smr.shop.cart.service.service.CartService;
import smr.shop.libs.common.dto.response.EmptyResponse;
import smr.shop.libs.common.dto.response.ErrorResponse;

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
    @Operation(summary = "Add product and stock to cart", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EmptyResponse.class))),
            @ApiResponse(responseCode = "401", description = "Authentication required!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Permission required!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not found!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<EmptyResponse> addToCart(@PathVariable Long productId, @PathVariable UUID stockId) {
        cartService.addProductToCart(productId, stockId);
        EmptyResponse response = EmptyResponse.builder().message("Product add to cart successfully!").build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/coupon/{couponCode}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Add coupon to cart", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EmptyResponse.class))),
            @ApiResponse(responseCode = "401", description = "Authentication required!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Permission required!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not found!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<EmptyResponse> addCouponToCart(@PathVariable String couponCode) {
        cartService.addCoupon(couponCode);
        EmptyResponse response = EmptyResponse.builder().message("Cart coupon is Added successfully!").build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{cartItemId}/increase")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Increase cart item request", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EmptyResponse.class))),
            @ApiResponse(responseCode = "401", description = "Authentication required!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Permission required!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not found!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<EmptyResponse> increaseCartItem(@PathVariable UUID cartItemId) {
        cartService.increase(cartItemId);
        EmptyResponse response = EmptyResponse.builder().message("Cart item is increased successfully!").build();
        return ResponseEntity.ok(response);
    }

//    ----------------------------------- Get -----------------------------------

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get user cart", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EmptyResponse.class))),
            @ApiResponse(responseCode = "401", description = "Authentication required!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Permission required!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not found!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<CartResponse> getUserCart() {
        CartResponse cartResponse = cartService.getUserCart();
        return ResponseEntity.ok(cartResponse);
    }

//    ----------------------------------- Delete -----------------------------------

    @DeleteMapping("/{cartItemId}/remove")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Remove item in cart request", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CartResponse.class))),
            @ApiResponse(responseCode = "401", description = "Authentication required!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Permission required!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not found!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<EmptyResponse> removeItemFromCart(@PathVariable UUID cartItemId) {
        cartService.deleteCartItem(cartItemId);
        EmptyResponse response = EmptyResponse.builder().message("Cart item is removed successfully!").build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{cartItemId}/decrease")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Decrease cart item request", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EmptyResponse.class))),
            @ApiResponse(responseCode = "401", description = "Authentication required!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Permission required!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not found!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<EmptyResponse> decreaseCartItem(@PathVariable UUID cartItemId) {
        cartService.decrease(cartItemId);
        EmptyResponse response = EmptyResponse.builder().message("Cart item is decreased successfully!").build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/coupon/remove")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "delete cart coupon request", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EmptyResponse.class))),
            @ApiResponse(responseCode = "401", description = "Authentication required!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Permission required!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not found!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<EmptyResponse> removeCouponFromCart() {
        cartService.removeCoupon();
        EmptyResponse response = EmptyResponse.builder().message("Cart coupon is removed successfully!").build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Cart clear request", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EmptyResponse.class))),
            @ApiResponse(responseCode = "401", description = "Authentication required!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Permission required!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not found!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<EmptyResponse> clearCart() {
        cartService.clearCart();
        EmptyResponse response = EmptyResponse.builder().message("Cart clear successfully").build();
        return ResponseEntity.ok().body(response);
    }

}
