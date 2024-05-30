package smr.shop.flash.service.service.impl;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smr.shop.flash.service.dto.request.FlashCreateRequest;
import smr.shop.flash.service.dto.request.FlashUpdateRequest;
import smr.shop.flash.service.dto.response.FlashItemResponse;
import smr.shop.flash.service.dto.response.FlashResponse;
import smr.shop.flash.service.exception.FlashServiceException;
import smr.shop.flash.service.mapper.FlashServiceMapper;
import smr.shop.flash.service.model.FlashEntity;
import smr.shop.flash.service.repository.FlashRepository;
import smr.shop.flash.service.service.FlashItemService;
import smr.shop.flash.service.service.FlashService;
import smr.shop.libs.common.constant.ServiceConstants;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class FlashServiceImpl implements FlashService {
    private final FlashRepository flashRepository;
    private final FlashServiceMapper flashServiceMapper;

    private final FlashItemService flashItemService;

    public FlashServiceImpl(FlashRepository flashRepository,
                            FlashServiceMapper flashServiceMapper,
                            FlashItemService flashItemService) {
        this.flashRepository = flashRepository;
        this.flashServiceMapper = flashServiceMapper;
        this.flashItemService = flashItemService;
    }


//    ----------------------------------- Create or Add -----------------------------------

    @Override
    @Transactional
    public void createFlash(FlashCreateRequest request) {
        FlashEntity flashEntity = flashServiceMapper.flashCreateRequestToFlashEntity(request);
        flashEntity.setCreatedAt(ZonedDateTime.now(ServiceConstants.ZONE_ID));
        flashEntity.setUpdatedAt(ZonedDateTime.now(ServiceConstants.ZONE_ID));
        flashRepository.save(flashEntity);
    }

    @Override
    public void addProduct(Long flashId, Long productId) {
        FlashEntity flashEntity = findById(flashId);
        flashItemService.addItem(flashEntity.getId(), productId);
    }

//    ----------------------------------- Update -----------------------------------

    @Override
    @Transactional
    public void updateFlash(Long flashId, FlashUpdateRequest request) {
        FlashEntity flashEntity = findById(flashId);
        FlashEntity updatedflashEntity = flashServiceMapper.flashUpdateRequestToFlashEntity(request, flashEntity);
        updatedflashEntity.setUpdatedAt(ZonedDateTime.now(ServiceConstants.ZONE_ID));
        flashRepository.save(flashEntity);
    }

//    ----------------------------------- Delete -----------------------------------

    @Override
    @Transactional
    public void deleteFlash(Long id) {
        FlashEntity flashEntity = findById(id);
        flashItemService.deleteItems(flashEntity.getId());
        flashRepository.delete(flashEntity);
    }

    @Override
    @Transactional
    public void removeItem(Long flashId, UUID flashItemId) {
        FlashEntity flashEntity = findById(flashId);
        flashItemService.deleteItem(flashEntity.getId(), flashItemId);
    }


//    ----------------------------------- Get -----------------------------------

    @Override
    public List<FlashResponse> getAllFlashes(Integer page) {
        Pageable pageable = PageRequest.of(page, ServiceConstants.pageSize);
        List<FlashEntity> flashEntityList = flashRepository.findAll(pageable).stream().toList();
        return flashServiceMapper.flashEntityToFlashResponse(flashEntityList);
    }

    @Override
    public List<FlashItemResponse> getItems(Long flashId, Integer page) {
        FlashEntity flashEntity = findById(flashId);
        return flashItemService.getItems(flashEntity.getId(), page);
    }

//    ----------------------------------- Extra -----------------------------------

    @Override
    public FlashEntity findById(Long id) {
        return flashRepository.findById(id)
                .orElseThrow(() -> new FlashServiceException("Flash Entity not found with id: " + id, HttpStatus.NOT_FOUND));
    }
}
