package smr.shop.brand.service.service;

import smr.shop.brand.service.dto.request.BrandCreateRequest;
import smr.shop.brand.service.dto.request.BrandUpdateRequest;
import smr.shop.brand.service.dto.response.BrandResponse;
import smr.shop.brand.service.model.BrandEntity;

import java.util.List;
import java.util.UUID;

public interface BrandService {
    BrandResponse create(BrandCreateRequest request);
    BrandResponse updateBrand(Long id, BrandUpdateRequest request);
    void deleteBrand(Long id);
    BrandResponse updateBrandImage(Long id,UUID imageId);
    void deleteBrandImage(Long id, UUID imageId);
    List<BrandResponse> getAllBrands(Integer page);
    BrandResponse getBrand(Long id);

    BrandEntity findById(Long id);
}
