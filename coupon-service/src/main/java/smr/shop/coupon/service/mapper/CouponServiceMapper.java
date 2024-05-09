package smr.shop.coupon.service.mapper;

import org.springframework.stereotype.Component;
import smr.shop.coupon.service.dto.request.CouponCreateRequest;
import smr.shop.coupon.service.dto.request.CouponUpdateRequest;
import smr.shop.coupon.service.dto.response.CouponResponse;
import smr.shop.coupon.service.dto.response.CouponUsageResponse;
import smr.shop.coupon.service.dto.response.UserCouponResponse;
import smr.shop.coupon.service.model.CouponEntity;
import smr.shop.coupon.service.model.CouponUsageEntity;
import smr.shop.coupon.service.model.UserCouponEntity;

import java.util.UUID;

/**
 * Author: Ali Gadashov
 * Version: v1.0
 * Date: 5/9/2024
 * Time: 3:13 PM
 */
@Component
public class CouponServiceMapper {
    public CouponEntity couponCreateResponseToCouponEntity(CouponCreateRequest request) {
        CouponEntity.CouponEntityBuilder builder = CouponEntity.builder();
        builder.id(UUID.randomUUID());
        builder.type(request.getType());
        builder.code(request.getCode());
        builder.details(request.getDetails());
        builder.discountType(request.getDiscountType());
        builder.amount(request.getAmount());
        builder.percentage(request.getPercentage());
        builder.maxDiscountPrice(request.getMaxDiscountPrice());
        builder.endDate(request.getEndDate());
        return builder.build();
    }

    public CouponEntity couponUpdateRequestToCouponEntity(CouponUpdateRequest request, CouponEntity entity) {
        entity.setType(request.getType());
        entity.setCode(request.getCode());
        entity.setDetails(request.getDetails());
        entity.setDiscountType(request.getDiscountType());
        entity.setAmount(request.getAmount());
        entity.setPercentage(request.getPercentage());
        entity.setMaxDiscountPrice(request.getMaxDiscountPrice());
        entity.setEndDate(request.getEndDate());
        return entity;
    }

    public CouponResponse couponEntityToCouponResponse(CouponEntity couponEntity) {
        return CouponResponse.builder()
                .id(couponEntity.getId())
                .code(couponEntity.getCode())
                .details(couponEntity.getDetails())
                .shopId(couponEntity.getShopId())
                .amount(couponEntity.getAmount())
                .percentage(couponEntity.getPercentage())
                .maxDiscountPrice(couponEntity.getMaxDiscountPrice())
                .endDate(couponEntity.getEndDate())
                .build();
    }


    public UserCouponResponse userCouponEntityToUserCouponResponse(UserCouponEntity userCouponEntity) {
        return UserCouponResponse.builder()
                .id(userCouponEntity.getId())
                .userId(userCouponEntity.getUserId())
                .build();
    }

    public CouponUsageResponse couponUsageEntityToCouponUsageResponse(CouponUsageEntity couponUsageEntity) {
        return CouponUsageResponse.builder()
                .id(couponUsageEntity.getId())
                .userId(couponUsageEntity.getUserId())
                .build();
    }

}
