package smr.shop.coupon.service.service.impl;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smr.shop.coupon.service.dto.request.CouponCreateRequest;
import smr.shop.coupon.service.dto.request.CouponUpdateRequest;
import smr.shop.coupon.service.dto.response.CouponResponse;
import smr.shop.coupon.service.exception.CouponServiceException;
import smr.shop.coupon.service.mapper.CouponServiceMapper;
import smr.shop.coupon.service.message.publisher.CouponDeleteMessagePublisher;
import smr.shop.coupon.service.model.CouponEntity;
import smr.shop.coupon.service.repository.CouponRepository;
import smr.shop.coupon.service.service.CouponService;
import smr.shop.coupon.service.service.CouponUsageService;
import smr.shop.libs.common.constant.ServiceConstants;
import smr.shop.libs.common.dto.message.CouponMessageModel;
import smr.shop.libs.common.helper.Money;
import smr.shop.libs.common.helper.UserHelper;
import smr.shop.libs.common.model.valueobject.CouponDiscountType;
import smr.shop.libs.common.model.valueobject.CouponType;
import smr.shop.libs.grpc.client.ShopGrpcClient;
import smr.shop.libs.grpc.coupon.CouponGrpcResponse;
import smr.shop.libs.grpc.coupon.CouponUsageGrpcResponse;
import smr.shop.libs.grpc.object.CouponGrpcCode;
import smr.shop.libs.grpc.object.CouponUsageGrpc;
import smr.shop.libs.grpc.product.shop.ShopGrpcResponse;

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

    // repository
    private final CouponRepository couponRepository;

    // internal service
    private final CouponUsageService couponUsageService;

    // service mapper
    private final CouponServiceMapper couponMapper;

    // rpc
    private final ShopGrpcClient shopGrpcClient;

    // message broker
    private final CouponDeleteMessagePublisher couponDeleteMessagePublisher;

    public CouponServiceImpl(CouponRepository couponRepository,
                             CouponUsageService couponUsageService,
                             CouponServiceMapper couponMapper,
                             ShopGrpcClient shopGrpcClient,
                             CouponDeleteMessagePublisher couponDeleteMessagePublisher) {
        this.couponRepository = couponRepository;
        this.couponUsageService = couponUsageService;
        this.couponMapper = couponMapper;
        this.shopGrpcClient = shopGrpcClient;
        this.couponDeleteMessagePublisher = couponDeleteMessagePublisher;
    }


//    ----------------------------------- Create or Add -----------------------------------

    @Override
    @Transactional
    public void createCouponWithAdmin(CouponCreateRequest request){
        CouponEntity couponEntity = couponMapper.couponCreateResponseToCouponEntity(request);
        validateCoupon(couponEntity);
        couponEntity.setId(UUID.randomUUID());
        couponEntity.setType(CouponType.ALL);
        couponEntity.setCreatedAt(ZonedDateTime.now(ServiceConstants.ZONE_ID));
        couponEntity.setUpdatedAt(ZonedDateTime.now(ServiceConstants.ZONE_ID));
        couponRepository.save(couponEntity);
    }

    @Override
    @Transactional
    public void createCoupon(CouponCreateRequest request) {
        UUID userId = UserHelper.getUserId();
        ShopGrpcResponse shopGrpcResponse = shopGrpcClient.getShopByUserId(userId.toString());
        CouponEntity couponEntity = couponMapper.couponCreateResponseToCouponEntity(request);
        validateCoupon(couponEntity);
        couponEntity.setId(UUID.randomUUID());
        couponEntity.setType(CouponType.SHOP);
        couponEntity.setShopId(shopGrpcResponse.getId());
        couponEntity.setCreatedAt(ZonedDateTime.now(ServiceConstants.ZONE_ID));
        couponEntity.setUpdatedAt(ZonedDateTime.now(ServiceConstants.ZONE_ID));
        couponRepository.save(couponEntity);
    }

//    ----------------------------------- Update -----------------------------------

    @Override
    @Transactional
    public void updateCouponWithAdmin(UUID couponId, CouponUpdateRequest request) {
        CouponEntity couponEntity = findById(couponId);
        couponEntity.setAmount(null);
        couponEntity.setPercentage(null);
        CouponEntity couponEntityUpdated = couponMapper.couponUpdateRequestToCouponEntity(request, couponEntity);
        validateCoupon(couponEntityUpdated);
        couponEntityUpdated.setUpdatedAt(ZonedDateTime.now(ServiceConstants.ZONE_ID));
        couponRepository.save(couponEntityUpdated);
    }

    @Override
    @Transactional
    public void updateCoupon(UUID couponId, CouponUpdateRequest request) {
        UUID userId = UserHelper.getUserId();
        ShopGrpcResponse shopGrpcResponse = shopGrpcClient.getShopByUserId(userId.toString());
        CouponEntity couponEntity = findById(couponId);
        if(!couponEntity.getShopId().equals(shopGrpcResponse.getId())){
            throw new CouponServiceException("you dont have a access to update coupon with id: " +  couponId,  HttpStatus.NOT_FOUND);
        }
        couponEntity.setAmount(null);
        couponEntity.setPercentage(null);
        CouponEntity couponEntityUpdated = couponMapper.couponUpdateRequestToCouponEntity(request, couponEntity);
        validateCoupon(couponEntityUpdated);
        couponEntityUpdated.setUpdatedAt(ZonedDateTime.now(ServiceConstants.ZONE_ID));
        couponRepository.save(couponEntityUpdated);
    }

//    ----------------------------------- Delete -----------------------------------

    @Override
    @Transactional
    public void deleteCoupon(UUID couponId) {
        UUID userId = UserHelper.getUserId();
        ShopGrpcResponse shopGrpcResponse = shopGrpcClient.getShopByUserId(userId.toString());
        CouponEntity couponEntity = findById(couponId);
        if(!couponEntity.getShopId().equals(shopGrpcResponse.getId())){
            throw new CouponServiceException("you dont have a access to delete coupon with id: " +  couponId,  HttpStatus.NOT_FOUND);
        }
        couponEntity.setIsDeleted(Boolean.TRUE);
        couponEntity.setUpdatedAt(ZonedDateTime.now(ServiceConstants.ZONE_ID));
        couponRepository.save(couponEntity);
        couponDeleteMessagePublisher.publish(CouponMessageModel.builder().id(couponId).build());
    }

    @Override
    @Transactional
    public void deleteCouponWithAdmin(UUID couponId) {
        CouponEntity couponEntity = findById(couponId);
        couponEntity.setIsDeleted(Boolean.TRUE);
        couponEntity.setUpdatedAt(ZonedDateTime.now(ServiceConstants.ZONE_ID));
        couponRepository.save(couponEntity);
        couponDeleteMessagePublisher.publish(CouponMessageModel.builder().id(couponId).build());
    }

//    ----------------------------------- Get -----------------------------------

    @Override
    public List<CouponResponse> getAllCoupons(Integer page) {
        Pageable pageable = PageRequest.of(page, ServiceConstants.pageSize);
        return couponRepository.findAllByIsDeletedFalse(pageable).map(couponMapper::couponEntityToCouponResponse).toList();
    }

    @Override
    public List<CouponResponse> getShopAllCoupons(Integer page) {
        UUID userId = UserHelper.getUserId();
        ShopGrpcResponse shopGrpcResponse = shopGrpcClient.getShopByUserId(userId.toString());
        Pageable pageable = PageRequest.of(page, ServiceConstants.pageSize);
        return couponRepository.findAllByShopIdAndIsDeletedFalse(shopGrpcResponse.getId(),pageable).map(couponMapper::couponEntityToCouponResponse).toList();
    }

    @Override
    public CouponResponse getCoupon(UUID couponId) {
        CouponEntity couponEntity = findById(couponId);

        return couponMapper.couponEntityToCouponResponse(couponEntity);
    }

//    ----------------------------------- Extra -----------------------------------

    @Override
    public CouponEntity findById(UUID couponId) {
        return couponRepository.findByIdAndIsDeletedFalse(couponId).orElseThrow(() -> new CouponServiceException("Coupon Not found With id : " + couponId, HttpStatus.NOT_FOUND));
    }

    @Override
    public CouponEntity findByCode(String couponCode) {
        return couponRepository.findByCode(couponCode).orElseThrow(() -> new CouponServiceException("Coupon Not found With code : " + couponCode, HttpStatus.NOT_FOUND));
    }

    @Override
    public CouponGrpcResponse getCouponDetail(CouponGrpcCode couponGrpcCode){
        CouponEntity couponEntity;
        couponEntity = findByCode(couponGrpcCode.getCode());
        Boolean isCouponExpired = isCouponExpired(couponEntity);
        return couponMapper.couponEntityToCouponGrpcResponse(couponEntity, isCouponExpired);
    }

    @Override
    public CouponUsageGrpcResponse getCouponUsage(CouponUsageGrpc couponUsageGrpc){
        CouponEntity couponEntity = findByCode(couponUsageGrpc.getCode());
        UUID userId = UUID.fromString(couponUsageGrpc.getUserId());
        Boolean isCouponUsed = couponUsageService.getCouponUsage(couponEntity.getId(), userId);
        return CouponUsageGrpcResponse.newBuilder().setIsUsed(isCouponUsed).build();
    }

    private Boolean isCouponExpired(CouponEntity couponEntity){
        return couponEntity.getExpirationTime().isAfter(ZonedDateTime.now(ServiceConstants.ZONE_ID));
    }

    private void validateCoupon(CouponEntity couponEntity) {
        if (couponEntity.getAmount() == null && couponEntity.getPercentage() == null) {
            throw new CouponServiceException("coupon amount or percentage can not be null", HttpStatus.BAD_REQUEST);
        }
        if (!Money.isGreaterThanZero(couponEntity.getMaxDiscountPrice())){
            throw new CouponServiceException("max discount price must be great than zero", HttpStatus.BAD_REQUEST);
        }
        if(couponEntity.getDiscountType() == CouponDiscountType.AMOUNT){
            if (!Money.isGreaterThanZero(couponEntity.getAmount())){
                throw new CouponServiceException("amount must be great than zero", HttpStatus.BAD_REQUEST);
            }
        }

        if(couponEntity.getDiscountType() == CouponDiscountType.PERCENT){
            if (couponEntity.getPercentage() <= 0){
                throw new CouponServiceException("percentage must be great than zero", HttpStatus.BAD_REQUEST);
            }
        }
    }
}
