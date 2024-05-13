package smr.shop.discount.service.service.impl;

import org.springframework.stereotype.Service;
import smr.shop.discount.service.dto.request.DiscountCreateRequest;
import smr.shop.discount.service.dto.request.DiscountUpdateRequest;
import smr.shop.discount.service.dto.response.DiscountResponse;
import smr.shop.discount.service.mapper.DiscountMapper;
import smr.shop.discount.service.model.DiscountEntity;
import smr.shop.discount.service.repository.DiscountRepository;
import smr.shop.discount.service.service.DiscountService;
@Service
public class DiscountServiceImpl implements DiscountService {
    private final DiscountMapper discountMapper;
    private final DiscountRepository discountRepository;

    public DiscountServiceImpl(DiscountMapper discountMapper, DiscountRepository discountRepository) {
        this.discountMapper = discountMapper;
        this.discountRepository = discountRepository;
    }

    @Override
    public DiscountResponse createDiscount(DiscountCreateRequest request) {
        DiscountEntity discountResponse= discountMapper.toDiscountResponse(request);
        discountResponse=discountRepository.save(discountResponse);
        return discountMapper.toDiscountResponse(discountResponse);
    }

    @Override
    public DiscountResponse updateDiscount(Long discountId, DiscountUpdateRequest request) {
        return null;
    }

    @Override
    public void deleteDiscount(Long discountId) {

    }

    @Override
    public DiscountResponse getById(Long id) {
        return null;
    }
}
