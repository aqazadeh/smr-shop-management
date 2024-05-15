package smr.shop.coupon.service.service;

import smr.shop.coupon.service.dto.request.CouponCreateRequest;
import smr.shop.coupon.service.dto.request.CouponUpdateRequest;
import smr.shop.coupon.service.dto.response.CouponResponse;
import smr.shop.coupon.service.model.CouponEntity;
import smr.shop.libs.grpc.coupon.CouponGrpcRequest;
import smr.shop.libs.grpc.coupon.CouponGrpcResponse;

import java.util.List;
import java.util.UUID;

/**
 * Author: Ali Gadashov
 * Version: v1.0
 * Date: 5/9/2024
 * Time: 2:49 PM
 */

public interface CouponService {

    void createCouponWithAdmin(CouponCreateRequest request);

    void createCoupon(CouponCreateRequest request);

    void updateCoupon(UUID couponId, CouponUpdateRequest request);

    void updateCouponWithAdmin(UUID couponId, CouponUpdateRequest request);

    void deleteCoupon(UUID couponId);

    void deleteCouponWithAdmin(UUID couponId);

    List<CouponResponse> getAllCoupons(Integer page);

    List<CouponResponse> getShopAllCoupons(Integer page);

    CouponResponse getCoupon(UUID couponId);

    CouponEntity findById(UUID couponId);

    CouponEntity findByCode(String couponCode);

    CouponGrpcResponse getCouponDetail(CouponGrpcRequest couponCodeGrpcRequest);
}
