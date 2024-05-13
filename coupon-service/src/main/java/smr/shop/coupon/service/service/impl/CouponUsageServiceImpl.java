package smr.shop.coupon.service.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import smr.shop.coupon.service.dto.response.CouponUsageResponse;
import smr.shop.coupon.service.exception.CouponUsageException;
import smr.shop.coupon.service.mapper.CouponServiceMapper;
import smr.shop.coupon.service.model.CouponUsageEntity;
import smr.shop.coupon.service.repository.CouponUsageRepository;
import smr.shop.coupon.service.service.CouponUsageService;

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
    public CouponUsageResponse createCouponUsage(UUID couponId) {


        if (couponUsageRepository.findCouponUsageByCouponId(couponId).isPresent()) {
            throw new CouponUsageException("This coupon already exist", HttpStatus.BAD_REQUEST);
        }

        CouponUsageEntity couponUsageEntity = couponUsageRepository.save(CouponUsageEntity.builder()
                .id(UUID.randomUUID())
// TODO : add user id
                .couponId(couponId)
                .build());
        return couponMapper.couponUsageEntityToCouponUsageResponse(couponUsageEntity);
    }

    @Override
    public void deleteCouponUsage(UUID couponUsageId) {
        CouponUsageEntity couponUsageEntity = findById(couponUsageId);
        couponUsageRepository.delete(couponUsageEntity);
        // TODO if need send kafka event
    }

    @Override
    public CouponUsageResponse getCouponUsage(UUID couponUsageId) {
        CouponUsageEntity couponUsageEntity = findById(couponUsageId);
        return couponMapper.couponUsageEntityToCouponUsageResponse(couponUsageEntity);
    }

    @Override
    public CouponUsageEntity findById(UUID couponUsageId) {
        return couponUsageRepository.findById(couponUsageId).orElseThrow(() -> new CouponUsageException("Coupon Not found With id : " + couponUsageId, HttpStatus.NOT_FOUND));
    }
}
