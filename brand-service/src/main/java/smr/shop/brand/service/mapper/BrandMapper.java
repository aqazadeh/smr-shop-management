package smr.shop.brand.service.mapper;

import org.springframework.stereotype.Component;
import smr.shop.brand.service.dto.request.BrandCreateRequest;
import smr.shop.brand.service.dto.request.BrandUpdateRequest;
import smr.shop.brand.service.dto.response.BrandResponse;
import smr.shop.brand.service.model.BrandEntity;

import java.util.UUID;

@Component
public class BrandMapper {
    public BrandEntity brandCreateResponseToBrandEntity(BrandCreateRequest request) {
        // check image id is exists
        BrandEntity.BrandEntityBuilder builder = BrandEntity.builder();
        builder.name(request.getName());
        builder.description(request.getDescription());
        return builder.build();
    }

    public BrandEntity brandUpdateRequestToBrandEntity(BrandUpdateRequest request,  BrandEntity entity) {
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
}