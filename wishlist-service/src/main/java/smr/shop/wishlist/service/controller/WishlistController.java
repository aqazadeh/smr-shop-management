package smr.shop.wishlist.service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smr.shop.libs.common.dto.response.EmptyResponse;
import smr.shop.libs.common.dto.response.ErrorResponse;
import smr.shop.wishlist.service.dto.response.WishlistResponse;
import smr.shop.wishlist.service.service.WishlistService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/1.0/wishlist")
public class WishlistController {
    private final WishlistService wishlistService;

    public WishlistController(WishlistService wishlistService) {
        this.wishlistService = wishlistService;
    }


//    ----------------------------------- Post -----------------------------------

    @PostMapping("/{productId}/add")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Add product to wishlist", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EmptyResponse.class))),
            @ApiResponse(responseCode = "401", description = "Authentication required!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Permission required!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not found!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<EmptyResponse> addProductToWishlist(@PathVariable Long productId) {
        wishlistService.addProductToWishlist(productId);
        EmptyResponse response = EmptyResponse.builder().message("Successfully added product to wishlist with product id: " + productId).build();
        return ResponseEntity.ok(response);
    }

//    ----------------------------------- Delete -----------------------------------

    @DeleteMapping("/{wishlistId}/remove")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Remove to wishlist", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EmptyResponse.class))),
            @ApiResponse(responseCode = "401", description = "Authentication required!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Permission required!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not found!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<EmptyResponse> removeToWishlist(@PathVariable UUID wishlistId) {
        wishlistService.deleteWishlist(wishlistId);
        EmptyResponse response = EmptyResponse.builder()
                .message("Successfully removed product to wishlist with product id: " + wishlistId)
                .build();
        return ResponseEntity.ok(response);
    }

//    ----------------------------------- Get -----------------------------------

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get wishlist", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = WishlistResponse.class))),
            @ApiResponse(responseCode = "401", description = "Authentication required!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Permission required!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not found!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<List<WishlistResponse>> getWishlist(@RequestParam(value = "page", defaultValue = "0") Integer page) {
        List<WishlistResponse> wishlistProducts = wishlistService.getAllWishlistProducts(page);
        return ResponseEntity.ok(wishlistProducts);
    }

}
