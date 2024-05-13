package smr.shop.coupon.service.service.impl;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import smr.shop.coupon.service.dto.request.CouponCreateRequest;
import smr.shop.coupon.service.dto.request.CouponUpdateRequest;
import smr.shop.coupon.service.dto.response.CouponResponse;
import smr.shop.coupon.service.exception.CouponException;
import smr.shop.coupon.service.mapper.CouponServiceMapper;
import smr.shop.coupon.service.model.CouponEntity;
import smr.shop.coupon.service.repository.CouponRepository;
import smr.shop.coupon.service.service.CouponService;
import smr.shop.libs.common.constant.ServiceConstants;

import java.util.List;
import java.util.UUID;


/**
 * Author: Ali Gadashov
 * Version: v1.0
 * Date: 5/9/2024
 * Time: 2:50 PM
 */
@Service
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;
    private final CouponServiceMapper couponMapper;

    public CouponServiceImpl(CouponRepository couponRepository, CouponServiceMapper couponMapper) {
        this.couponRepository = couponRepository;
        this.couponMapper = couponMapper;
    }

    @Override
    public CouponResponse createCoupon(CouponCreateRequest request) {
        CouponEntity couponEntity = couponMapper.couponCreateResponseToCouponEntity(request);
        couponEntity = couponRepository.save(couponEntity);
        return couponMapper.couponEntityToCouponResponse(couponEntity);
    }

    @Override
    public CouponResponse updateCoupon(UUID couponId, CouponUpdateRequest request) {

        // TODO SEND KAFKA EVENT

        CouponEntity couponEntity = findById(couponId);
        CouponEntity couponEntityUpdated = couponMapper.couponUpdateRequestToCouponEntity(request, couponEntity);
        couponEntity = couponRepository.save(couponEntityUpdated);
        return couponMapper.couponEntityToCouponResponse(couponEntity);
    }

    @Override
    public void deleteCoupon(UUID couponId) {
        CouponEntity couponEntity = findById(couponId);
        couponRepository.delete(couponEntity);
        // TODO if need send kafka event
    }

    @Override
    public List<CouponResponse> getAllCoupons(Integer page) {
        Pageable pageable = PageRequest.of(page, ServiceConstants.pageSize);
        return couponRepository.findAll(pageable).map(couponMapper::couponEntityToCouponResponse).toList();
    }

    @Override
    public CouponResponse getCoupon(UUID couponId) {
        CouponEntity couponEntity = findById(couponId);
        return couponMapper.couponEntityToCouponResponse(couponEntity);
    }

    @Override
    public CouponEntity findById(UUID couponId) {
        return couponRepository.findById(couponId).orElseThrow(() -> new CouponException("Coupon Not found With id : " + couponId, HttpStatus.NOT_FOUND));
    }
}
