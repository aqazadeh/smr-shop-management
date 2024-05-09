package smr.shop.coupon.service.service.impl;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import smr.shop.coupon.service.dto.response.UserCouponResponse;
import smr.shop.coupon.service.exception.UserCouponException;
import smr.shop.coupon.service.mapper.CouponServiceMapper;
import smr.shop.coupon.service.model.UserCouponEntity;
import smr.shop.coupon.service.repository.UserCouponRepository;
import smr.shop.coupon.service.service.UserCouponService;
import smr.shop.libs.common.constant.ServiceConstants;

import java.util.List;
import java.util.UUID;

/**
 * Author: Ali Gadashov
 * Version: v1.0
 * Date: 5/9/2024
 * Time: 4:45 PM
 */
@Service
public class UserCouponServiceImpl implements UserCouponService {


    private final UserCouponRepository userCouponRepository;
    private final CouponServiceMapper couponMapper;

    public UserCouponServiceImpl(UserCouponRepository userCouponRepository, CouponServiceMapper couponMapper) {
        this.userCouponRepository = userCouponRepository;
        this.couponMapper = couponMapper;
    }

    @Override
    public UserCouponResponse createUserCoupon(UUID couponId) {
        UserCouponEntity userCouponEntity = userCouponRepository.save(UserCouponEntity.builder()
                .id(UUID.randomUUID())
// TODO : add user id
                .couponId(couponId)
                .build());

        return couponMapper.userCouponEntityToUserCouponResponse(userCouponEntity);
    }

    @Override
    public void deleteUserCoupon(UUID userCouponId) {

        UserCouponEntity userCouponEntity = findById(userCouponId);
        userCouponRepository.delete(userCouponEntity);

    }

    @Override
    public List<UserCouponResponse> getAllUserCoupons(Integer page) {
        Pageable pageable = PageRequest.of(page, ServiceConstants.pageSize);
        return userCouponRepository.findAll(pageable).map(couponMapper::userCouponEntityToUserCouponResponse).toList();

    }

    @Override
    public UserCouponResponse getUserCoupon(UUID userCouponId) {
        UserCouponEntity userCouponEntity = findById(userCouponId);
        return couponMapper.userCouponEntityToUserCouponResponse(userCouponEntity);
    }

    @Override
    public UserCouponEntity findById(UUID userCouponId) {
        return userCouponRepository.findById(userCouponId).orElseThrow(() -> new UserCouponException("Coupon Not found With id : " + userCouponId, HttpStatus.NOT_FOUND));

    }
}
