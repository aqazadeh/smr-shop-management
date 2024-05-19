package smr.shop.coupon.service.mapper;

import org.springframework.stereotype.Component;
import smr.shop.coupon.service.dto.request.CouponCreateRequest;
import smr.shop.coupon.service.dto.request.CouponUpdateRequest;
import smr.shop.coupon.service.dto.response.CouponResponse;
import smr.shop.coupon.service.model.CouponEntity;
import smr.shop.coupon.service.model.CouponUsageEntity;
import smr.shop.libs.common.model.valueobject.CouponDiscountType;
import smr.shop.libs.common.dto.message.UseCouponMessageModel;
import smr.shop.libs.grpc.coupon.CouponGrpcResponse;

import java.util.UUID;

/**
 * Author: Ali Gadashov
 * Version: v1.0
 * Date: 5/9/2024
 * Time: 3:13 PM
import smr.shop.coupon.service.dto.response.CouponUsageResponse;
 */
@Component
public class CouponServiceMapper {
    public CouponEntity couponCreateResponseToCouponEntity(CouponCreateRequest request) {
        CouponEntity.CouponEntityBuilder builder = CouponEntity.builder();
        builder.id(UUID.randomUUID());
        builder.code(request.getCode());
        builder.details(request.getDetails());
        builder.discountType(request.getType());
        if (request.getType() == CouponDiscountType.AMOUNT) {
            builder.amount(request.getAmount());
        }
        if(request.getType() == CouponDiscountType.PERCENT){
            builder.percentage(request.getPercentage());
        }
        builder.maxDiscountPrice(request.getMaxDiscountPrice());
        builder.expirationTime(request.getEndDate());
        return builder.build();
    }

    public CouponEntity couponUpdateRequestToCouponEntity(CouponUpdateRequest request, CouponEntity entity) {
        entity.setCode(request.getCode());
        entity.setDetails(request.getDetails());
        entity.setAmount(request.getAmount());
        entity.setPercentage(request.getPercentage());
        entity.setMaxDiscountPrice(request.getMaxDiscountPrice());
        entity.setExpirationTime(request.getEndDate());
        return entity;
    }

    public CouponResponse couponEntityToCouponResponse(CouponEntity couponEntity) {
        return CouponResponse.builder()
                .id(couponEntity.getId())
                .type(couponEntity.getDiscountType())
                .shopId(couponEntity.getShopId())
                .code(couponEntity.getCode())
                .details(couponEntity.getDetails())
                .amount(couponEntity.getAmount())
                .percentage(couponEntity.getPercentage())
                .maxDiscountPrice(couponEntity.getMaxDiscountPrice())
                .endDate(couponEntity.getExpirationTime())
                .build();
    }

    public CouponGrpcResponse couponEntityToCouponGrpcResponse(CouponEntity couponEntity, Boolean isCouponExpired) {
        return CouponGrpcResponse.newBuilder()
                .setId(couponEntity.getId().toString())
                .setShopId(couponEntity.getShopId())
                .setCode(couponEntity.getCode())
                .setCouponType(couponEntity.getType().name())
                .setPercentage(couponEntity.getPercentage())
                .setMaxDiscountPrice(couponEntity.getMaxDiscountPrice().doubleValue())
                .setAmount(couponEntity.getAmount().doubleValue())
                .setIsExpired(isCouponExpired)
                .build();
    }

    public CouponUsageEntity useCouponMessageModelToCouponUsageEntity(UseCouponMessageModel useCouponMessageModel) {
        return CouponUsageEntity.builder()
                .id(UUID.randomUUID())
                .userId(useCouponMessageModel.getUserId())
                .couponId(useCouponMessageModel.getCouponId())
                .build();
    }

}
