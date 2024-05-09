package smr.shop.coupon.service.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import smr.shop.coupon.service.dto.response.UserCouponResponse;
import smr.shop.coupon.service.service.UserCouponService;

import java.util.List;
import java.util.UUID;

/**
 * Author: Ali Gadashov
 * Version: v1.0
 * Date: 5/9/2024
 * Time: 5:54 PM
 */
@RestController
@RequestMapping("/api/v1/user-coupon")
public class UserCouponController {
    private final UserCouponService userCouponService;

    public UserCouponController(UserCouponService userCouponService) {
        this.userCouponService = userCouponService;
    }

    @PostMapping("/{couponId}")
    public ResponseEntity<UserCouponResponse> createUserCoupon(@PathVariable UUID couponId) {
        UserCouponResponse userCouponResponse = userCouponService.createUserCoupon(couponId);
        return ResponseEntity.ok(userCouponResponse);
    }

    @DeleteMapping("/{userCouponId}")
    public ResponseEntity<Void> deleteUserCoupon(@PathVariable UUID userCouponId) {
        userCouponService.deleteUserCoupon(userCouponId);
        return ResponseEntity.noContent().build();
    }
    @GetMapping
    public ResponseEntity<List<UserCouponResponse>> getAllUserCoupon(Integer page) {
        List<UserCouponResponse> allUserCoupon = userCouponService.getAllUserCoupons(page);
        return ResponseEntity.ok(allUserCoupon);
    }
    @GetMapping("/{userCouponId}")
    public ResponseEntity<UserCouponResponse> getUserCoupon(@PathVariable UUID userCouponId) {
        UserCouponResponse userCouponResponse = userCouponService.getUserCoupon(userCouponId);
        return ResponseEntity.ok(userCouponResponse);
    }
}
