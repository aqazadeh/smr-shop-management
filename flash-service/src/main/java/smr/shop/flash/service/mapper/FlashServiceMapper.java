package smr.shop.flash.service.mapper;

import org.springframework.stereotype.Component;
import smr.shop.flash.service.dto.request.FLashDealCreateRequest;
import smr.shop.flash.service.dto.request.FLashDealUpdateRequest;
import smr.shop.flash.service.dto.response.FlashDealResponse;
import smr.shop.flash.service.model.FlashDealEntity;

import java.util.List;

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

    public FlashDealEntity flashDealCreateRequestToFlashDealEntity(FLashDealCreateRequest request) {
        return FlashDealEntity.builder()
                .title(request.getTitle())
                .slug(request.getSlug())
                .endDate(request.getEndDate())
                .startDate(request.getStartDate())
                .isActive(request.getIsActive())
                .build();
    }

    public FlashDealEntity flashDealUpdateRequestToFlashDealEntity(FLashDealUpdateRequest request, FlashDealEntity entity) {
        entity.setTitle(request.getTitle());
        entity.setSlug(request.getSlug());
        entity.setEndDate(request.getEndDate());
        entity.setStartDate(request.getStartDate());
        entity.setIsActive(request.getIsActive());
        return entity;
    }
}
