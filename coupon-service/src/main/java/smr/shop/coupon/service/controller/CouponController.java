package smr.shop.coupon.service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import smr.shop.coupon.service.dto.request.CouponCreateRequest;
import smr.shop.coupon.service.dto.request.CouponUpdateRequest;
import smr.shop.coupon.service.dto.response.CouponResponse;
import smr.shop.coupon.service.service.CouponService;
import smr.shop.libs.common.dto.response.EmptyResponse;
import smr.shop.libs.common.dto.response.ErrorResponse;

import java.util.List;
import java.util.UUID;

/**
 * Author: Ali Gadashov
 * Version: v1.0
 * Date: 5/9/2024
 * Time: 5:53 PM
 */
@RestController
@RequestMapping("/api/1.0/coupons")
public class CouponController {

    private final CouponService couponService;

    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

//    ----------------------------------- Post -----------------------------------

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyAuthority('ROLE_SELLER', 'ROLE_ADMIN')")
    @Operation(summary = "Create coupon", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Created successfully", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EmptyResponse.class))),
            @ApiResponse(responseCode = "401", description = "Authentication required!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Permission required!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<EmptyResponse> createCoupon(@RequestBody @Valid CouponCreateRequest request) {
        couponService.createCoupon(request);
        EmptyResponse response = EmptyResponse.builder()
                .message("Coupon created successfully!")
                .build();
        return ResponseEntity.ok(response);
    }


//    ----------------------------------- Patch -----------------------------------

    @PutMapping("/{couponId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ROLE_SELLER')")
    @Operation(summary = "Update Coupon with couponId", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Updated successfully!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EmptyResponse.class))),
            @ApiResponse(responseCode = "401", description = "Authentication required!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Permission required!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not found!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<EmptyResponse> updateCoupon(@PathVariable UUID couponId, @RequestBody @Valid CouponUpdateRequest request) {
        couponService.updateCoupon(couponId, request);
        EmptyResponse response = EmptyResponse.builder()
                .message("Coupon updated successfully with id: " + couponId)
                .build();
        return ResponseEntity.ok(response);
    }

//    ----------------------------------- Delete -----------------------------------

    @DeleteMapping("/{couponId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ROLE_SELLER')")
    @Operation(summary = "Delete Coupon with id", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Deleted successfully!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EmptyResponse.class))),
            @ApiResponse(responseCode = "401", description = "Authentication required!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Permission required!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not found!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<EmptyResponse> deleteCoupon(@PathVariable UUID couponId) {
        couponService.deleteCoupon(couponId);
        EmptyResponse response = EmptyResponse.builder()
                .message("Coupon deleted successfully with id: " + couponId)
                .build();
        return ResponseEntity.ok(response);
    }

//    ----------------------------------- Get -----------------------------------

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ROLE_SELLER')")
    @Operation(summary = "Get all shop coupons", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CouponResponse.class))))
    })
    public ResponseEntity<List<CouponResponse>> getAllShopCoupon(@RequestParam(value = "page", defaultValue = "0") Integer page) {
        List<CouponResponse> allCoupon = couponService.getShopAllCoupons(page);
        return ResponseEntity.ok(allCoupon);
    }

    @GetMapping("/admin")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "ADMIN get all coupons", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CouponResponse.class))))
    })
    public ResponseEntity<List<CouponResponse>> getAllCoupon(@RequestParam(value = "page", defaultValue = "0") Integer page) {
        List<CouponResponse> allCoupon = couponService.getAllCoupons(page);
        return ResponseEntity.ok(allCoupon);
    }

    @GetMapping("/{couponId}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @Operation(summary = "ADMIN get all coupons", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = CouponResponse.class)))),
            @ApiResponse(responseCode = "404", description = "Not found!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<CouponResponse> getCouponById(@PathVariable UUID couponId) {
        CouponResponse couponResponse = couponService.getCoupon(couponId);
        return ResponseEntity.ok(couponResponse);
    }

}
