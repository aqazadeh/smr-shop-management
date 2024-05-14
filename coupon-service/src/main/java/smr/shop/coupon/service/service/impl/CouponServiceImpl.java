package smr.shop.coupon.service.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import smr.shop.coupon.service.dto.request.CouponCreateRequest;
import smr.shop.coupon.service.dto.request.CouponUpdateRequest;
import smr.shop.coupon.service.dto.response.CouponResponse;
import smr.shop.coupon.service.exception.CouponServiceException;
import smr.shop.coupon.service.grpc.client.CouponClientGrpcService;
import smr.shop.coupon.service.mapper.CouponServiceMapper;
import smr.shop.coupon.service.message.publisher.CouponDeleteMessagePublisher;
import smr.shop.coupon.service.model.CouponEntity;
import smr.shop.coupon.service.model.valueobject.CouponType;
import smr.shop.coupon.service.repository.CouponRepository;
import smr.shop.coupon.service.service.CouponService;
import smr.shop.coupon.service.service.CouponUsageService;
import smr.shop.libs.common.Helper.UserHelper;
import smr.shop.libs.common.constant.ServiceConstants;
import smr.shop.libs.common.dto.message.CouponMessageModel;
import smr.shop.libs.grpc.coupon.CouponGrpcRequest;
import smr.shop.libs.grpc.coupon.CouponGrpcResponse;
import smr.shop.libs.grpc.product.shop.ShopGrpcResponse;

import java.time.ZoneId;
import java.time.ZonedDateTime;
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
    private final CouponClientGrpcService couponClientGrpcService;
    private final CouponDeleteMessagePublisher couponDeleteMessagePublisher;
    private final CouponUsageService couponUsageService;

    public CouponServiceImpl(CouponRepository couponRepository,
                             CouponServiceMapper couponMapper,
                             CouponClientGrpcService couponClientGrpcService,
                             CouponDeleteMessagePublisher couponDeleteMessagePublisher,
                             CouponUsageService couponUsageService) {
        this.couponRepository = couponRepository;
        this.couponMapper = couponMapper;
        this.couponClientGrpcService = couponClientGrpcService;
        this.couponDeleteMessagePublisher = couponDeleteMessagePublisher;
        this.couponUsageService = couponUsageService;
    }

    @Override
    public CouponResponse createCoupon(CouponCreateRequest request) {
        CouponEntity couponEntity = couponMapper.couponCreateResponseToCouponEntity(request);
        couponEntity = couponRepository.save(couponEntity);
        return couponMapper.couponEntityToCouponResponse(couponEntity);
    }

    @Override
    public CouponResponse updateCoupon(UUID couponId, CouponUpdateRequest request) {

        CouponEntity couponEntity = findById(couponId);
        CouponEntity couponEntityUpdated = couponMapper.couponUpdateRequestToCouponEntity(request, couponEntity);
        couponEntity = couponRepository.save(couponEntityUpdated);
        return couponMapper.couponEntityToCouponResponse(couponEntity);
    }

    @Override
    public void deleteCoupon(UUID couponId) {
        CouponEntity couponEntity = findById(couponId);
        if(couponEntity.getType() != CouponType.SHOP){
            throw new CouponServiceException("you dont have a access to delete coupon with id: " +  couponId,  HttpStatus.NOT_FOUND);
        }
        couponRepository.delete(couponEntity);
        couponDeleteMessagePublisher.publish(CouponMessageModel.builder().id(couponId).build());
    }

    @Override
    public void deleteCouponWithAdmin(UUID couponId) {
        CouponEntity couponEntity = findById(couponId);
        couponRepository.delete(couponEntity);
        couponDeleteMessagePublisher.publish(CouponMessageModel.builder().id(couponId).build());
    }

    @Override
    public List<CouponResponse> getAllCoupons(Integer page) {
        Pageable pageable = PageRequest.of(page, ServiceConstants.pageSize);
        return couponRepository.findAll(pageable).map(couponMapper::couponEntityToCouponResponse).toList();
    }

    @Override
    public List<CouponResponse> getShopAllCoupons(Integer page) {
        Long userId = UserHelper.getUserId();
        ShopGrpcResponse shopGrpcResponse = couponClientGrpcService.getShopByUserId(userId);

        Pageable pageable = PageRequest.of(page, ServiceConstants.pageSize);
        Page<CouponEntity> all = couponRepository.findAllByShopId(shopGrpcResponse .getShopId(),pageable);
        return all.map(couponMapper::couponEntityToCouponResponse).toList();
    }

    @Override
    public CouponResponse getCoupon(UUID couponId) {
        CouponEntity couponEntity = findById(couponId);
        return couponMapper.couponEntityToCouponResponse(couponEntity);
    }

    @Override
    public CouponEntity findById(UUID couponId) {
        CouponEntity couponEntity = couponRepository.findById(couponId).orElseThrow(() -> new CouponServiceException("Coupon Not found With id : " + couponId, HttpStatus.NOT_FOUND));
        return validateCoupon(couponEntity);
    }

    @Override
    public CouponEntity findByCode(String couponCode) {
        CouponEntity couponEntity = couponRepository.findByCode(couponCode).orElseThrow(() -> new CouponServiceException("Coupon Not found With code : " + couponCode, HttpStatus.NOT_FOUND));
        return validateCoupon(couponEntity);
    }

    @Override
    public CouponGrpcResponse getCouponDetail(CouponGrpcRequest couponGrpcRequest){
        CouponEntity couponEntity;
        if (couponGrpcRequest.getId().isBlank()) {
            couponEntity = findByCode(couponGrpcRequest.getCode());
        }else{
            couponEntity = findById(UUID.fromString(couponGrpcRequest.getId()));
        }
        boolean couponUsage = couponUsageService.getCouponUsage(couponEntity.getId(), couponGrpcRequest.getUserId());
        CouponGrpcResponse couponGrpcResponse = couponMapper.couponEntityToCouponGrpcResponse(couponEntity, couponUsage);
        return couponGrpcResponse;
    }



    private CouponEntity validateCoupon(CouponEntity couponEntity) {
        if (couponEntity.getExpirationTime().compareTo(ZonedDateTime.now(ZoneId.of(ServiceConstants.UTC))) < 0) {
            throw new CouponServiceException("coupon expired with id: + " + couponEntity.getId(), HttpStatus.BAD_REQUEST);
        }
        return couponEntity;
    }
}
