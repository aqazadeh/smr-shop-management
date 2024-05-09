package smr.shop.flash.service.service;

import smr.shop.flash.service.dto.request.FlashDealCreateRequest;
import smr.shop.flash.service.dto.request.FlashDealUpdateRequest;
import smr.shop.flash.service.dto.response.FlashDealResponse;
import smr.shop.flash.service.model.FlashDealEntity;

import java.util.List;

public interface FlashDealService {


    FlashDealResponse createFlashDeal(FlashDealCreateRequest request);

    FlashDealResponse updateFlashDeal(Long id, FlashDealUpdateRequest request);

    void deleteFlashDeal(Long id);

    FlashDealResponse getFlashDealById(Long id);

    List<FlashDealResponse> getAllFlashDeals(Integer page);

    //
    FlashDealEntity findById(Long id);
}
