package smr.shop.flash.service.mapper;

import org.springframework.stereotype.Component;
import smr.shop.flash.service.dto.request.FlashDealCreateRequest;
import smr.shop.flash.service.dto.request.FlashDealProductCreateRequest;
import smr.shop.flash.service.dto.request.FlashDealProductUpdateRequest;
import smr.shop.flash.service.dto.request.FlashDealUpdateRequest;
import smr.shop.flash.service.dto.response.FlashDealProductResponse;
import smr.shop.flash.service.dto.response.FlashDealResponse;
import smr.shop.flash.service.model.FlashDealEntity;
import smr.shop.flash.service.model.FlashDealProductEntity;

import java.util.List;
import java.util.UUID;

@Component
public class FlashServiceMapper {
    public FlashDealResponse flashDealEntityToFlashDealResponse(FlashDealEntity entity) {
        return FlashDealResponse.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .slug(entity.getSlug())
                .endDate(entity.getEndDate())
                .startDate(entity.getStartDate())
                .isActive(entity.getIsActive())
                .build();
    }

    public List<FlashDealResponse> flashDealEntityToFlashDealResponse(List<FlashDealEntity> entityList) {
        return entityList.stream().map(this::flashDealEntityToFlashDealResponse).toList();
    }

    public FlashDealEntity flashDealCreateRequestToFlashDealEntity(FlashDealCreateRequest request) {
        return FlashDealEntity.builder()
                .title(request.getTitle())
                .slug(request.getSlug())
                .endDate(request.getEndDate())
                .startDate(request.getStartDate())
                .isActive(request.getIsActive())
                .build();
    }

    public FlashDealEntity flashDealUpdateRequestToFlashDealEntity(FlashDealUpdateRequest request, FlashDealEntity entity) {
        entity.setTitle(request.getTitle());
        entity.setSlug(request.getSlug());
        entity.setEndDate(request.getEndDate());
        entity.setStartDate(request.getStartDate());
        entity.setIsActive(request.getIsActive());
        return entity;
    }


    public FlashDealProductResponse flashDealProductEntityToFlashDealProductResponse(FlashDealProductEntity entity) {
        return FlashDealProductResponse.builder()
                .id(UUID.randomUUID())
                .flashDealEntityId(entity.getFlashDealEntityId())
                .productId(entity.getProductId())
                .build();
    }

    public List<FlashDealProductResponse> flashDealProductEntityToFlashDealProductResponse(List<FlashDealProductEntity> entityList) {
        return entityList.stream().map(this::flashDealProductEntityToFlashDealProductResponse).toList();
    }

    public FlashDealProductEntity flashDealProductCreateRequestToFlashDealProductEntity(FlashDealProductCreateRequest request) {
        return FlashDealProductEntity.builder()
                .flashDealEntityId(request.getFlashDealEntityId())
                .productId(request.getProductId())
                .build();
    }

    public FlashDealProductEntity flashDealProductUpdateRequestToFlashDealProductEntity(FlashDealProductUpdateRequest request, FlashDealProductEntity entity) {
        entity.setFlashDealEntityId(request.getProductId());
        entity.setProductId(request.getProductId());
        return entity;
    }
}
