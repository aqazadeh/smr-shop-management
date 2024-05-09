package smr.shop.flash.service.service.impl;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import smr.shop.flash.service.dto.request.FLashDealCreateRequest;
import smr.shop.flash.service.dto.request.FLashDealUpdateRequest;
import smr.shop.flash.service.dto.response.FlashDealResponse;
import smr.shop.flash.service.exception.FlashDealException;
import smr.shop.flash.service.mapper.FlashServiceMapper;
import smr.shop.flash.service.model.FlashDealEntity;
import smr.shop.flash.service.repository.FlashDealRepository;
import smr.shop.flash.service.service.FlashDealService;
import smr.shop.libs.common.constant.ServiceConstants;

import java.util.List;

public class FlashDealServiceImpl implements FlashDealService {
    private final FlashDealRepository flashDealRepository;
    private final FlashServiceMapper flashServiceMapper;

    public FlashDealServiceImpl(FlashDealRepository flashDealRepository,
                                FlashServiceMapper flashServiceMapper) {
        this.flashDealRepository = flashDealRepository;
        this.flashServiceMapper = flashServiceMapper;
    }

    @Override
    public FlashDealResponse createDeal(FLashDealCreateRequest request) {
        //TODO GRPC get image url
        FlashDealEntity flashDealEntity = flashServiceMapper.flashDealCreateRequestToFlashDealEntity(request);
        FlashDealEntity saveEntity = flashDealRepository.save(flashDealEntity);
        return flashServiceMapper.flashDealEntityToFlashDealResponse(saveEntity);
    }

    @Override
    public FlashDealResponse updateDeal(Long id, FLashDealUpdateRequest request) {
        FlashDealEntity flashDeal = findById(id);
        FlashDealEntity flashDealEntity = flashServiceMapper.flashDealUpdateRequestToFlashDealEntity(request, flashDeal);
        FlashDealEntity saveEntity = flashDealRepository.save(flashDealEntity);
        // TODO SEND KAFKA EVENT
        return flashServiceMapper.flashDealEntityToFlashDealResponse(saveEntity);
    }

    @Override
    public void deleteDeal(Long id) {
        FlashDealEntity flashDeal = findById(id);
        // TODO if need send kafka event
        flashDealRepository.delete(flashDeal);
    }

    @Override
    public FlashDealResponse getByIdDeal(Long id) {
        FlashDealEntity flashDealEntity = findById(id);
        //TODO GRPC GET IMAGE ID
        return flashServiceMapper.flashDealEntityToFlashDealResponse(flashDealEntity);
    }

    @Override
    public List<FlashDealResponse> getAllDeals(Integer page) {
        Pageable pageable = PageRequest.of(page, ServiceConstants.pageSize);
        List<FlashDealEntity> flashDealEntityList = flashDealRepository.findAll(pageable).stream().toList();
        // TODO GET IMAGES GRPC
        return flashServiceMapper.flashDealEntityToFlashDealResponse(flashDealEntityList);
    }

    @Override
    public FlashDealEntity findById(Long id) {
        return flashDealRepository.findById(id)
                .orElseThrow(() -> new FlashDealException("Flash Deal not found with id: " + id, HttpStatus.NOT_FOUND));
    }
}
