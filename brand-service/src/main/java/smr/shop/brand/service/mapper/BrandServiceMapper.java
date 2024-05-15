package smr.shop.brand.service.mapper;

import org.springframework.stereotype.Component;
import smr.shop.brand.service.dto.request.BrandCreateRequest;
import smr.shop.brand.service.dto.request.BrandUpdateRequest;
import smr.shop.brand.service.dto.request.CategoryCreateRequest;
import smr.shop.brand.service.dto.request.CategoryUpdateRequest;
import smr.shop.brand.service.dto.response.BrandResponse;
import smr.shop.brand.service.dto.response.CategoryResponse;
import smr.shop.brand.service.model.BrandEntity;
import smr.shop.brand.service.model.CategoryEntity;
import smr.shop.libs.common.constant.ServiceConstants;
import smr.shop.libs.common.dto.message.BrandDeleteMessageModel;
import smr.shop.libs.common.dto.message.BrandImageDeleteMessageModel;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@Component
public class BrandServiceMapper {
    public BrandEntity brandCreateResponseToBrandEntity(BrandCreateRequest request) {
        BrandEntity.BrandEntityBuilder builder = BrandEntity.builder();
        builder.name(request.getName());
        builder.description(request.getDescription());
        builder.createdAt(ZonedDateTime.now(ZoneId.of(ServiceConstants.UTC)));
        builder.updatedAt(ZonedDateTime.now(ZoneId.of(ServiceConstants.UTC)));
        return builder.build();
    }

    public BrandEntity brandUpdateRequestToBrandEntity(BrandUpdateRequest request, BrandEntity entity) {
        entity.setName(request.getName());
        entity.setSlug(request.getSlug());
        entity.setDescription(request.getDescription());
        entity.setUpdatedAt(ZonedDateTime.now(ZoneId.of(ServiceConstants.UTC)));
        return entity;
    }

    public BrandResponse brandEntityToBrandResponse(BrandEntity brandEntity) {
        // get image id is upload service
        return BrandResponse.builder()
                .id(brandEntity.getId())
                .name(brandEntity.getName())
                .slug(brandEntity.getSlug())
                .description(brandEntity.getDescription())
                .build();
    }


    public BrandImageDeleteMessageModel brandEntityToBrandImageDeleteMessageModel(BrandEntity brandEntity) {
        return BrandImageDeleteMessageModel.builder()
                .imageId(brandEntity.getImageId())
                .build();
    }

    public BrandDeleteMessageModel brandEntityToBrandDeleteMessageModel(BrandEntity brandEntity){
        return BrandDeleteMessageModel.builder()
                .id(brandEntity.getId())
                .build();
    }

    public CategoryEntity categoryCreateRequestToCategoryEntity(CategoryCreateRequest request) {
        CategoryEntity.CategoryEntityBuilder builder = CategoryEntity.builder();
        builder.name(request.getName());
        builder.description(request.getDescription());
        builder.parentId(request.getParentId());
        builder.createdAt(ZonedDateTime.now(ZoneId.of(ServiceConstants.UTC)));
        builder.updatedAt(ZonedDateTime.now(ZoneId.of(ServiceConstants.UTC)));
        return builder.build();
    }

    public CategoryEntity categoryUpdateRequestToCategoryEntity(CategoryEntity category, CategoryUpdateRequest request) {
        category.setName(request.getName());
        category.setSlug(request.getSlug());
        category.setDescription(request.getDescription());
        category.setParentId(request.getParentId());
        category.setUpdatedAt(ZonedDateTime.now(ZoneId.of(ServiceConstants.UTC)));
        return category;
    }

    public CategoryResponse categoryEntityToCategoryResponse(CategoryEntity category) {
        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .slug(category.getSlug())
                .description(category.getDescription())
                .build();
    }
}
