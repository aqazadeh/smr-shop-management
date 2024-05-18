package smr.shop.flash.service.service.impl;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import smr.shop.flash.service.dto.request.FlashDealProductCreateRequest;
import smr.shop.flash.service.dto.request.FlashDealProductUpdateRequest;
import smr.shop.flash.service.dto.response.FlashDealProductResponse;
import smr.shop.flash.service.exception.FlashDealProductException;
import smr.shop.flash.service.mapper.FlashServiceMapper;
import smr.shop.flash.service.model.FlashDealProductEntity;
import smr.shop.flash.service.repository.FlashDealProductRepository;
import smr.shop.flash.service.service.FlashDealProductService;
import smr.shop.libs.common.constant.ServiceConstants;

import java.util.List;
import java.util.UUID;

/**
 * Author: Ali Gadashov
 * Version: v1.0
 * Date: 5/9/2024
 * Time: 8:00 PM
 */

@Service
public class FlashDealProductServiceImpl implements FlashDealProductService {
    private final FlashDealProductRepository flashDealProductRepository;
    private final FlashServiceMapper flashServiceMapper;

    public FlashDealProductServiceImpl(FlashDealProductRepository flashDealProductRepository,
                                       FlashServiceMapper flashServiceMapper) {
        this.flashDealProductRepository = flashDealProductRepository;
        this.flashServiceMapper = flashServiceMapper;
    }

//    ----------------------------------- Create or Add -----------------------------------

    @Override
    public FlashDealProductResponse createFlashDealProduct(FlashDealProductCreateRequest request) {
        //TODO GRPC get image url
        FlashDealProductEntity flashDealProductEntity = flashServiceMapper.flashDealProductCreateRequestToFlashDealProductEntity(request);
        FlashDealProductEntity saveEntity = flashDealProductRepository.save(flashDealProductEntity);
        return flashServiceMapper.flashDealProductEntityToFlashDealProductResponse(saveEntity);
    }

//    ----------------------------------- Update -----------------------------------

    @Override
    public FlashDealProductResponse updateFlashDealProduct(UUID flashDealProductId, FlashDealProductUpdateRequest request) {
        FlashDealProductEntity flashDealProduct = findById(flashDealProductId);
        FlashDealProductEntity flashDealProductEntity = flashServiceMapper.flashDealProductUpdateRequestToFlashDealProductEntity(request, flashDealProduct);
        FlashDealProductEntity saveEntity = flashDealProductRepository.save(flashDealProductEntity);
        // TODO SEND KAFKA EVENT
        return flashServiceMapper.flashDealProductEntityToFlashDealProductResponse(saveEntity);
    }

//    ----------------------------------- Delete -----------------------------------

    @Override
    public void deleteFlashDealProduct(UUID flashDealProductId) {
        FlashDealProductEntity flashDealProduct = findById(flashDealProductId);
        // TODO if need send kafka event
        flashDealProductRepository.delete(flashDealProduct);
    }

//    ----------------------------------- Get -----------------------------------

    @Override
    public FlashDealProductResponse getFlashDealProductById(UUID flashDealProductId) {
        FlashDealProductEntity flashDealProductEntity = findById(flashDealProductId);
        //TODO GRPC GET IMAGE ID
        return flashServiceMapper.flashDealProductEntityToFlashDealProductResponse(flashDealProductEntity);
    }

    @Override
    public List<FlashDealProductResponse> getAllFlashDealProducts(Integer page) {
        Pageable pageable = PageRequest.of(page, ServiceConstants.pageSize);
        List<FlashDealProductEntity> flashDealProductEntityList = flashDealProductRepository.findAll(pageable).stream().toList();
        // TODO GET IMAGES GRPC
        return flashServiceMapper.flashDealProductEntityToFlashDealProductResponse(flashDealProductEntityList);
    }

//    ----------------------------------- Extra -----------------------------------

    @Override
    public FlashDealProductEntity findById(UUID flashDealProductId) {
        return flashDealProductRepository.findById(flashDealProductId)
                .orElseThrow(() -> new FlashDealProductException("Flash Deal Product not found with id: " + flashDealProductId, HttpStatus.NOT_FOUND));
    }
}
