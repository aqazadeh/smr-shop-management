package smr.shop.flash.service.service;

import smr.shop.flash.service.dto.request.FlashCreateRequest;
import smr.shop.flash.service.dto.request.FlashUpdateRequest;
import smr.shop.flash.service.dto.response.FlashItemResponse;
import smr.shop.flash.service.dto.response.FlashResponse;
import smr.shop.flash.service.model.FlashEntity;

import java.util.List;
import java.util.UUID;

public interface FlashService {


    void createFlash(FlashCreateRequest request);

    void addProduct(Long flashId, Long productId);

    void updateFlash(Long flashId, FlashUpdateRequest request);

    void deleteFlash(Long flashId);

    void removeItem(Long flashId, UUID flashItemId);

    List<FlashResponse> getAllFlashes(Integer page);

    List<FlashItemResponse> getItems(Long flashId, Integer page);

    FlashEntity findById(Long id);
}
