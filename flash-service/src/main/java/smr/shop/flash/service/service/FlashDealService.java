package smr.shop.flash.service.service;

import smr.shop.flash.service.dto.request.FLashDealCreateRequest;
import smr.shop.flash.service.dto.request.FLashDealUpdateRequest;
import smr.shop.flash.service.dto.response.FlashDealResponse;
import smr.shop.flash.service.model.FlashDealEntity;

import java.util.List;

public interface FlashDealService {

    FlashDealResponse createDeal(FLashDealCreateRequest request);
    FlashDealResponse updateDeal(Long id, FLashDealUpdateRequest request);
    void deleteDeal(Long id);
    FlashDealResponse getByIdDeal(Long id);
    List<FlashDealResponse> getAllDeals(Integer page);

    //
    FlashDealEntity findById(Long id);
}
