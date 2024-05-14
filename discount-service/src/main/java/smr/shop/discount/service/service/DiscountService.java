package smr.shop.discount.service.service;

import smr.shop.discount.service.dto.request.DiscountCreateRequest;
import smr.shop.discount.service.dto.request.DiscountUpdateRequest;
import smr.shop.discount.service.dto.response.DiscountResponse;
import smr.shop.discount.service.model.DiscountEntity;

public interface DiscountService {
    void createDiscount(DiscountCreateRequest request);
    void updateDiscount(Long discountId,DiscountUpdateRequest request);
    void deleteDiscount(Long discountId);

    DiscountResponse getById(Long id);
    DiscountEntity findById(Long id);
}
