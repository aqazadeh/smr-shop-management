package smr.shop.discount.service.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import smr.shop.discount.service.dto.request.DiscountCreateRequest;
import smr.shop.discount.service.dto.request.DiscountUpdateRequest;
import smr.shop.discount.service.dto.response.DiscountResponse;
import smr.shop.discount.service.exception.DiscountException;
import smr.shop.discount.service.mapper.DiscountMapper;
import smr.shop.discount.service.model.DiscountEntity;
import smr.shop.discount.service.repository.DiscountRepository;
import smr.shop.discount.service.service.DiscountService;
import smr.shop.libs.common.constant.ServiceConstants;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Service
public class DiscountServiceImpl implements DiscountService {
    private final DiscountMapper discountMapper;
    private final DiscountRepository discountRepository;

    public DiscountServiceImpl(DiscountMapper discountMapper, DiscountRepository discountRepository) {
        this.discountMapper = discountMapper;
        this.discountRepository = discountRepository;
    }

    @Override
    public void createDiscount(DiscountCreateRequest request) {
        DiscountEntity discountEntity = discountMapper.discountCreateRequestToDiscountEntity(request);
        discountRepository.save(discountEntity);
    }

    @Override
    public void updateDiscount(Long discountId, DiscountUpdateRequest request) {
        DiscountEntity discountEntity= findById(discountId);
        DiscountEntity updatedDiscountEntity = discountMapper.discountUpdateRequestToDiscountEntity(request, discountEntity);
        discountRepository.save(updatedDiscountEntity);
    }

    @Override
    public void deleteDiscount(Long discountId) {
        discountRepository.delete(findById(discountId));
    }

    @Override
    public DiscountResponse getDiscountById(Long id) {
        DiscountEntity discountEntity = findById(id);

        DiscountResponse discountResponse = discountMapper.discountEntityToDiscountResponse(discountEntity);

        return discountResponse;
    }

    @Override
    public DiscountEntity findById(Long id) {
        return discountRepository.findById(id).orElseThrow(() -> new DiscountException("Discount Not found With id : " + id, HttpStatus.NOT_FOUND));
    }
}
