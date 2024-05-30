package smr.shop.flash.service.mapper;

import org.springframework.stereotype.Component;
import smr.shop.flash.service.dto.request.FlashCreateRequest;
import smr.shop.flash.service.dto.request.FlashUpdateRequest;
import smr.shop.flash.service.dto.response.FlashItemResponse;
import smr.shop.flash.service.dto.response.FlashResponse;
import smr.shop.flash.service.model.FlashEntity;
import smr.shop.flash.service.model.FlashItemEntity;

import java.util.List;

@Component
public class FlashServiceMapper {
    public FlashResponse flashEntityToFlashResponse(FlashEntity entity) {
        return FlashResponse.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .slug(entity.getSlug())
                .endDate(entity.getEndDate())
                .startDate(entity.getStartDate())
                .isActive(entity.getIsActive())
                .build();
    }

    public List<FlashResponse> flashEntityToFlashResponse(List<FlashEntity> entityList) {
        return entityList.stream().map(this::flashEntityToFlashResponse).toList();
    }

    public FlashEntity flashCreateRequestToFlashEntity(FlashCreateRequest request) {
        return FlashEntity.builder()
                .title(request.getTitle())
                .endDate(request.getEndDate())
                .startDate(request.getStartDate())
                .build();
    }

    public FlashEntity flashUpdateRequestToFlashEntity(FlashUpdateRequest request, FlashEntity entity) {
        entity.setTitle(request.getTitle());
        entity.setSlug(request.getSlug());
        entity.setEndDate(request.getEndDate());
        entity.setStartDate(request.getStartDate());
        return entity;
    }


    public FlashItemResponse flashItemEntityToFlashItemResponse(FlashItemEntity entity) {
        return FlashItemResponse.builder()
                .id(entity.getId())
                .flashId(entity.getFlashId())
                .productId(entity.getProductId())
                .build();
    }

    public List<FlashItemResponse> flashItemEntityToFlashItemResponse(List<FlashItemEntity> entityList) {
        return entityList.stream().map(this::flashItemEntityToFlashItemResponse).toList();
    }
}
