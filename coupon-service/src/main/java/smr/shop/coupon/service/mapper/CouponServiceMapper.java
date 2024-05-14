package smr.shop.coupon.service.mapper;

import org.springframework.stereotype.Component;
import smr.shop.coupon.service.dto.request.CouponCreateRequest;
import smr.shop.coupon.service.dto.request.CouponUpdateRequest;
import smr.shop.coupon.service.dto.response.CouponResponse;
import smr.shop.coupon.service.dto.response.CouponUsageResponse;
import smr.shop.coupon.service.model.CouponEntity;
import smr.shop.coupon.service.model.CouponUsageEntity;
import smr.shop.libs.grpc.coupon.CouponGrpcResponse;

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
        builder.expirationTime(request.getEndDate());
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
        entity.setExpirationTime(request.getEndDate());
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
                .endDate(couponEntity.getExpirationTime())
                .build();
    }



    public CouponUsageResponse couponUsageEntityToCouponUsageResponse(CouponUsageEntity couponUsageEntity) {
        return CouponUsageResponse.builder()
                .id(couponUsageEntity.getId())
                .userId(couponUsageEntity.getUserId())
                .build();
    }

    public CouponGrpcResponse couponEntityToCouponGrpcResponse(CouponEntity couponEntity, boolean couponUsage) {
        return CouponGrpcResponse.newBuilder()
                .setId(couponEntity.getId().toString())
                .setShopId(couponEntity.getShopId())
                .setCode(couponEntity.getCode())
                .setCouponType(smr.shop.libs.grpc.coupon.CouponType.valueOf(couponEntity.getType().name()))
                .setPercentage(couponEntity.getPercentage())
                .setMaxDiscountPrice(couponEntity.getMaxDiscountPrice().doubleValue())
                .setAmount(couponEntity.getAmount().doubleValue())
                .setIsUsed(couponUsage)
                .build();
    }

}
