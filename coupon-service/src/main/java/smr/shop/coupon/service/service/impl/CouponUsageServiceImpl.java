package smr.shop.coupon.service.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import smr.shop.coupon.service.exception.CouponServiceException;
import smr.shop.coupon.service.mapper.CouponServiceMapper;
import smr.shop.coupon.service.model.CouponUsageEntity;
import smr.shop.coupon.service.repository.CouponUsageRepository;
import smr.shop.coupon.service.service.CouponUsageService;
import smr.shop.libs.common.dto.message.UseCouponMessageModel;

import java.util.UUID;


/**
 * Author: Ali Gadashov
 * Version: v1.0
 * Date: 5/9/2024
 * Time: 4:48 PM
 */
@Service
public class CouponUsageServiceImpl implements CouponUsageService {
    private final CouponUsageRepository couponUsageRepository;
    private final CouponServiceMapper couponMapper;

    public CouponUsageServiceImpl(CouponUsageRepository couponUsageRepository, CouponServiceMapper couponMapper) {
        this.couponUsageRepository = couponUsageRepository;
        this.couponMapper = couponMapper;
    }

    @Override
    public void createCouponUsage(UseCouponMessageModel useCouponMessageModel) {
        if (couponUsageRepository.findCouponUsageByCouponId(useCouponMessageModel.getCouponId()).isPresent()) {
            throw new CouponServiceException("This coupon already used ", HttpStatus.BAD_REQUEST);
        }
        CouponUsageEntity couponUsage = couponMapper.useCouponMessageModelToCouponUsageEntity(useCouponMessageModel);
        couponUsageRepository.save(couponUsage);
    }

    @Override
    public Boolean getCouponUsage(UUID couponId, UUID userId) {
        return couponUsageRepository.findByCouponIdAndUserId(couponId, userId).isPresent();
    }
}
