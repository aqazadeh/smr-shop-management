package smr.shop.category.brand.service.mapper;

import org.springframework.stereotype.Component;
import smr.shop.category.brand.service.dto.request.BrandCreateRequest;
import smr.shop.category.brand.service.dto.request.BrandUpdateRequest;
import smr.shop.category.brand.service.dto.request.CategoryCreateRequest;
import smr.shop.category.brand.service.dto.request.CategoryUpdateRequest;
import smr.shop.category.brand.service.dto.response.BrandResponse;
import smr.shop.category.brand.service.dto.response.CategoryResponse;
import smr.shop.category.brand.service.model.BrandEntity;
import smr.shop.category.brand.service.model.CategoryEntity;
import smr.shop.libs.common.dto.message.BrandMessageModel;
import smr.shop.libs.common.dto.message.CategoryMessageModel;
import smr.shop.libs.common.dto.message.UploadMessageModel;
import smr.shop.libs.grpc.brand.BrandGrpcResponse;
import smr.shop.libs.grpc.category.CategoryGrpcResponse;

@Component
public class CategoryBrandServiceMapper {
    public BrandEntity brandCreateResponseToBrandEntity(BrandCreateRequest request) {
        return BrandEntity.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();
    }

    public BrandEntity brandUpdateRequestToBrandEntity(BrandUpdateRequest request, BrandEntity entity) {
        entity.setName(request.getName());
        entity.setSlug(request.getSlug());
        entity.setDescription(request.getDescription());
        return entity;
    }

    public BrandResponse brandEntityToBrandResponse(BrandEntity brandEntity) {
        return BrandResponse.builder()
                .id(brandEntity.getId())
                .name(brandEntity.getName())
                .slug(brandEntity.getSlug() + "-" + brandEntity.getId())
                .description(brandEntity.getDescription())
                .imageUrl(brandEntity.getImageUrl())
                .build();
    }

    public BrandGrpcResponse brandEntityToBrandGrpcResponse(BrandEntity brandEntity) {
        return BrandGrpcResponse.newBuilder()
                .setId(brandEntity.getId())
                .setName(brandEntity.getName())
                .setSlug(brandEntity.getSlug() + "-" + brandEntity.getId())
                .setDescription(brandEntity.getDescription())
                .setImageUrl(brandEntity.getImageUrl())
                .build();
    }

    public BrandMessageModel brandEntityToBrandDeleteMessageModel(BrandEntity brandEntity) {
        return BrandMessageModel.builder()
                .id(brandEntity.getId())
                .description(brandEntity.getDescription())
                .name(brandEntity.getName())
                .imageUrl(brandEntity.getImageUrl())
                .slug(brandEntity.getSlug() + "-" + brandEntity.getId())
                .build();
    }

    public CategoryEntity categoryCreateRequestToCategoryEntity(CategoryCreateRequest request) {
        return CategoryEntity.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();
    }

    public CategoryEntity categoryUpdateRequestToCategoryEntity(CategoryEntity category, CategoryUpdateRequest request) {
        category.setName(request.getName());
        category.setSlug(request.getSlug());
        category.setDescription(request.getDescription());
        return category;
    }

    public CategoryResponse categoryEntityToCategoryResponse(CategoryEntity category) {

        return CategoryResponse.builder()
                .id(category.getId())
                .name(category.getName())
                .slug(category.getSlug() + "-" + category.getId())
                .description(category.getDescription())
                .children(category.getChildren().stream().map(this::categoryEntityToCategoryResponse).toList())
                .build();
    }

    public CategoryGrpcResponse categoryEntityToCategoryGrpcResponse(CategoryEntity categoryEntity) {
        return CategoryGrpcResponse.newBuilder()
                .setId(categoryEntity.getId())
                .setName(categoryEntity.getName())
                .setSlug(categoryEntity.getSlug() + "-" + categoryEntity.getId())
                .build();
    }

    public CategoryMessageModel categoryEntityToCategoryMessageModel(CategoryEntity categoryEntity) {
        return CategoryMessageModel.builder()
                .id(categoryEntity.getId())
                .name(categoryEntity.getName())
                .description(categoryEntity.getDescription())
                .slug(categoryEntity.getSlug() + "-" + categoryEntity.getId())
                .parentId(categoryEntity.getParent() ==null ? null: categoryEntity.getParent().getId())
                .build();
    }

    public UploadMessageModel brandEntityToUploadMessageModel(BrandEntity brandEntity) {
        return UploadMessageModel.builder()
                .imageUrl(brandEntity.getImageUrl())
                .build();
    }
}
