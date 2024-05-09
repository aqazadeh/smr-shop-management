package smr.shop.coupon.service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smr.shop.coupon.service.dto.response.CouponUsageResponse;
import smr.shop.coupon.service.service.CouponUsageService;

import java.util.UUID;

/**
 * Author: Ali Gadashov
 * Version: v1.0
 * Date: 5/9/2024
 * Time: 5:54 PM
 */

@RestController
@RequestMapping("/api/v1/coupon-usage")
public class CouponUsageController {

    private final CouponUsageService couponUsageService;

    public CouponUsageController(CouponUsageService couponUsageService) {
        this.couponUsageService = couponUsageService;
    }

    @PostMapping("/{couponId}")
    public ResponseEntity<CouponUsageResponse> createCouponUsage(@PathVariable UUID couponId) {
        CouponUsageResponse couponUsageResponse = couponUsageService.createCouponUsage(couponId);
        return ResponseEntity.ok(couponUsageResponse);
    }

    @DeleteMapping("/{couponUsageId}")
    public ResponseEntity<Void> deleteCouponUsage(@PathVariable UUID couponUsageId) {
        couponUsageService.deleteCouponUsage(couponUsageId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{couponUsageId}")
    public ResponseEntity<CouponUsageResponse> getCouponUsage(@PathVariable UUID couponUsageId) {
        CouponUsageResponse couponUsageResponse = couponUsageService.getCouponUsage(couponUsageId);
        return ResponseEntity.ok(couponUsageResponse);
    }
}
