package smr.shop.discount.service.service;

import smr.shop.discount.service.dto.request.DiscountCreateRequest;
import smr.shop.discount.service.dto.request.DiscountUpdateRequest;
import smr.shop.discount.service.dto.response.DiscountResponse;

public interface DiscountService {
    DiscountResponse createDiscount(DiscountCreateRequest request);
    DiscountResponse updateDiscount(Long discountId,DiscountUpdateRequest request);
    void deleteDiscount(Long discountId);

    DiscountResponse getById(Long id);
}
