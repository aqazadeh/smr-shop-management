package smr.shop.shop.service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;
import smr.shop.libs.common.dto.response.EmptyResponse;
import smr.shop.libs.common.model.valueobject.ShopStatus;
import smr.shop.shop.service.dto.request.CreateShopRequest;
import smr.shop.shop.service.dto.request.UpdateShopAddressRequest;
import smr.shop.shop.service.dto.request.UpdateShopRequest;
import smr.shop.shop.service.dto.response.ShopAddressResponse;
import smr.shop.shop.service.dto.response.ShopResponse;
import smr.shop.shop.service.service.ShopService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/1.0/shop")
public class ShopController {

    private final ShopService shopService;

    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

//    ----------------------------------- Post -----------------------------------

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ROLE_USER')")
    @Operation(summary = "Created shop", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EmptyResponse.class))),
            @ApiResponse(responseCode = "401", description = "Authentication required!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Permission required!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not found!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<EmptyResponse> createShop(@RequestBody CreateShopRequest request) {
        shopService.createShop(request);
        EmptyResponse response = EmptyResponse.builder()
                .message("Shop created successfully")
                .build();
        return ResponseEntity.ok(response);
    }

//    ----------------------------------- Put or Patch -----------------------------------

    @PutMapping("/{shopId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Updated shop", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EmptyResponse.class))),
            @ApiResponse(responseCode = "401", description = "Authentication required!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Permission required!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not found!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<EmptyResponse> updateShop(@PathVariable Long shopId, @RequestBody UpdateShopRequest request) {
        shopService.updateShop(shopId, request);
        EmptyResponse response = EmptyResponse.builder()
                .message("Shop updated successfully with shop id: " + shopId)
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{shopId}/status")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "Updated shop status", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EmptyResponse.class))),
            @ApiResponse(responseCode = "401", description = "Authentication required!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Permission required!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not found!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<EmptyResponse> updateShopStatus(@PathVariable Long shopId, @RequestParam ShopStatus status) {
        shopService.updateShopStatus(shopId, status);
        EmptyResponse response = EmptyResponse.builder()
                .message("Shop status successfully updated with shop id: " + shopId)
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{shopId}/address")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ROLE_SELLER')")
    @Operation(summary = "Updated shop address", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EmptyResponse.class))),
            @ApiResponse(responseCode = "401", description = "Authentication required!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Permission required!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not found!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<EmptyResponse> updateShopAddress(@PathVariable Long shopId, @RequestBody UpdateShopAddressRequest request) {
        shopService.updateShopAddress(shopId, request);
        EmptyResponse response = EmptyResponse.builder()
                .message("Shop address successfully with shop id: " + shopId)
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{shopId}/image/{imageId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ROLE_SELLER')")
    @Operation(summary = "Updated shop logo", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EmptyResponse.class))),
            @ApiResponse(responseCode = "401", description = "Authentication required!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Permission required!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not found!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<EmptyResponse> updateShopLogo(@PathVariable Long shopId, @PathVariable UUID imageId) {
        shopService.updateShopLogo(shopId, imageId);
        EmptyResponse response = EmptyResponse.builder()
                .message("Shop logo successfully with shop id: " + shopId)
                .build();
        return ResponseEntity.ok(response);
    }

//    ----------------------------------- Delete -----------------------------------

    @DeleteMapping("/{shopId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_SELLER')")
    @Operation(summary = "Deleted shop", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EmptyResponse.class))),
            @ApiResponse(responseCode = "401", description = "Authentication required!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Permission required!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not found!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<EmptyResponse> deleteShop(@PathVariable Long shopId) {
        shopService.deleteShop(shopId);
        EmptyResponse response = EmptyResponse.builder()
                .message("Shop deleted successfully with shop id: " + shopId)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{shopId}/image")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Remove shop logo", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EmptyResponse.class))),
            @ApiResponse(responseCode = "401", description = "Authentication required!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Permission required!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not found!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<EmptyResponse> removeShopLogo(@PathVariable Long shopId) {
        shopService.deleteShopImage(shopId);
        EmptyResponse response = EmptyResponse.builder()
                .message("Shop image successfully deleted with shop id: " + shopId)
                .build();
        return ResponseEntity.ok(response);
    }

//    ----------------------------------- Get -----------------------------------

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get all shop", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ShopResponse.class))),
            @ApiResponse(responseCode = "401", description = "Authentication required!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Permission required!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not found!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<List<ShopResponse>> getAllShop(@RequestParam(value = "page", defaultValue = "0") Integer page) {
        List<ShopResponse> shopResponses = shopService.getAllShop(page);
        return ResponseEntity.ok(shopResponses);
    }

    @GetMapping("/{shopId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get shop", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ShopResponse.class))),
            @ApiResponse(responseCode = "401", description = "Authentication required!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Permission required!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not found!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<ShopResponse> getShopById(@PathVariable Long shopId) {
        ShopResponse shopResponse = shopService.getShopById(shopId);
        return ResponseEntity.ok(shopResponse);
    }

    @GetMapping("/{shopId}/address")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get shop address", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ShopAddressResponse.class))),
            @ApiResponse(responseCode = "401", description = "Authentication required!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Permission required!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not found!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<ShopAddressResponse> getShopAddress(@PathVariable Long shopId) {
        ShopAddressResponse shopAddressResponse = shopService.getShopAddress(shopId);
        return ResponseEntity.ok(shopAddressResponse);
    }
}
