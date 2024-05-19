package smr.shop.coupon.service.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smr.shop.coupon.service.dto.request.CouponCreateRequest;
import smr.shop.coupon.service.dto.request.CouponUpdateRequest;
import smr.shop.coupon.service.dto.response.CouponResponse;
import smr.shop.coupon.service.service.CouponService;
import smr.shop.libs.common.dto.response.EmptyResponse;

import java.util.List;
import java.util.UUID;

/**
 * Author: Ali Gadashov
 * Version: v1.0
 * Date: 5/9/2024
 * Time: 5:53 PM
 */
@RestController
@RequestMapping("/api/1.0/coupon")
public class CouponController {

    private final CouponService couponService;

    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

//    ----------------------------------- Post -----------------------------------

    @PostMapping
    public ResponseEntity<EmptyResponse> createCoupon(@RequestBody @Valid CouponCreateRequest request) {
        couponService.createCoupon(request);
        EmptyResponse response = EmptyResponse.builder()
                .message("Coupon created successfully!")
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/admin")
    public ResponseEntity<EmptyResponse> createCouponWithAdmin(@RequestBody @Valid CouponCreateRequest request) {
        couponService.createCouponWithAdmin(request);
        EmptyResponse response = EmptyResponse.builder()
                .message("Coupon created successfully!")
                .build();
        return ResponseEntity.ok(response);
    }

//    ----------------------------------- Patch -----------------------------------

    @PatchMapping("/{couponId}")
    public ResponseEntity<EmptyResponse> updateCoupon(@PathVariable UUID couponId, @RequestBody @Valid CouponUpdateRequest request) {
        couponService.updateCoupon(couponId, request);
        EmptyResponse response = EmptyResponse.builder()
                .message("Coupon updated successfully with id: " + couponId)
                .build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/admin/{couponId}")
    public ResponseEntity<EmptyResponse> updateCouponWithAdmin(@PathVariable UUID couponId, @RequestBody @Valid CouponUpdateRequest request) {
        couponService.updateCouponWithAdmin(couponId, request);
        EmptyResponse response = EmptyResponse.builder()
                .message("Coupon updated successfully with id: " + couponId)
                .build();
        return ResponseEntity.ok(response);
    }

//    ----------------------------------- Delete -----------------------------------

    @DeleteMapping("/{couponId}")
    public ResponseEntity<EmptyResponse> deleteCoupon(@PathVariable UUID couponId) {
        couponService.deleteCoupon(couponId);
        EmptyResponse response = EmptyResponse.builder()
                .message("Coupon deleted successfully with id: " + couponId)
                .build();
        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/admin/{couponId}")
    public ResponseEntity<EmptyResponse> deleteCouponWithAdmin(@PathVariable UUID couponId) {
        couponService.deleteCouponWithAdmin(couponId);
        EmptyResponse response = EmptyResponse.builder()
                .message("Coupon deleted successfully with id: " + couponId)
                .build();
        return ResponseEntity.ok(response);
    }

//    ----------------------------------- Get -----------------------------------

    @GetMapping
    public ResponseEntity<List<CouponResponse>> getAllShopCoupon(@RequestParam(value = "page", defaultValue = "0") Integer page) {
        List<CouponResponse> allCoupon = couponService.getShopAllCoupons(page);
        return ResponseEntity.ok(allCoupon);
    }

    @GetMapping("/admin")
    public ResponseEntity<List<CouponResponse>> getAllCoupon(@RequestParam(value = "page", defaultValue = "0") Integer page) {
        List<CouponResponse> allCoupon = couponService.getAllCoupons(page);
        return ResponseEntity.ok(allCoupon);
    }

    @GetMapping("/{couponId}")
    public ResponseEntity<CouponResponse> getCouponById(@PathVariable UUID couponId) {
        CouponResponse couponResponse = couponService.getCoupon(couponId);
        return ResponseEntity.ok(couponResponse);
    }

}
