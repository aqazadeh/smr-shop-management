package smr.shop.coupon.service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smr.shop.coupon.service.dto.request.CouponCreateRequest;
import smr.shop.coupon.service.dto.request.CouponUpdateRequest;
import smr.shop.coupon.service.dto.response.CouponResponse;
import smr.shop.coupon.service.service.CouponService;

import java.util.List;
import java.util.UUID;

/**
 * Author: Ali Gadashov
 * Version: v1.0
 * Date: 5/9/2024
 * Time: 5:53 PM
 */
@RestController
@RequestMapping("/api/v1/coupon")
public class CouponController {

    private final CouponService couponService;

    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    @PostMapping
    public ResponseEntity<CouponResponse> createCoupon(@RequestBody CouponCreateRequest request) {
        CouponResponse couponResponse = couponService.createCoupon(request);
        return ResponseEntity.ok(couponResponse);
    }

    @PatchMapping("/{couponId}")
    public ResponseEntity<CouponResponse> updateCoupon(@PathVariable UUID couponId, @RequestBody CouponUpdateRequest request) {
        CouponResponse couponResponse = couponService.updateCoupon(couponId, request);
        return ResponseEntity.ok(couponResponse);
    }

    @DeleteMapping("/{couponId}")
    public ResponseEntity<Void> deleteCoupon(@PathVariable UUID couponId) {
        couponService.deleteCoupon(couponId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<CouponResponse>> getAllCoupon(Integer page) {
        List<CouponResponse> allCoupon = couponService.getAllCoupons(page);
        return ResponseEntity.ok(allCoupon);
    }

    @GetMapping("/{couponId}")
    public ResponseEntity<CouponResponse> getCouponById(@PathVariable UUID couponId) {
        CouponResponse couponResponse = couponService.getCoupon(couponId);
        return ResponseEntity.ok(couponResponse);
    }
}
