package smr.shop.brand.service.mapper;

import org.springframework.stereotype.Component;
import smr.shop.brand.service.dto.request.BrandCreateRequest;
import smr.shop.brand.service.dto.request.BrandUpdateRequest;
import smr.shop.brand.service.dto.response.BrandResponse;
import smr.shop.brand.service.model.BrandEntity;
import smr.shop.libs.common.dto.message.BrandDeleteMessageModel;
import smr.shop.libs.common.dto.message.BrandImageDeleteMessageModel;

@Component
public class BrandServiceMapper {
    public BrandEntity brandCreateResponseToBrandEntity(BrandCreateRequest request) {
        BrandEntity.BrandEntityBuilder builder = BrandEntity.builder();
        builder.name(request.getName());
        builder.description(request.getDescription());
        return builder.build();
    }

    public BrandEntity brandUpdateRequestToBrandEntity(BrandUpdateRequest request, BrandEntity entity) {
        entity.setName(request.getName());
        entity.setSlug(request.getSlug());
        entity.setDescription(request.getDescription());
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
}
