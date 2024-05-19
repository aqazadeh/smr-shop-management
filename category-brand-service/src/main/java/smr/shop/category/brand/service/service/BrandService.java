package smr.shop.category.brand.service.service;

import smr.shop.category.brand.service.dto.request.BrandCreateRequest;
import smr.shop.category.brand.service.dto.request.BrandUpdateRequest;
import smr.shop.category.brand.service.dto.response.BrandResponse;
import smr.shop.category.brand.service.model.BrandEntity;
import smr.shop.libs.grpc.brand.BrandGrpcResponse;
import smr.shop.libs.grpc.object.BrandGrpcId;

import java.util.List;
import java.util.UUID;

public interface BrandService {
    void createBrand(BrandCreateRequest request);

    void updateBrand(Long id, BrandUpdateRequest request);

    void deleteBrand(Long id);

    void updateBrandImage(Long id, UUID imageId);

    void deleteBrandImage(Long id);

    List<BrandResponse> getAllBrands(Integer page);

    BrandResponse getBrand(Long id);

    BrandEntity findById(Long id);

    BrandGrpcResponse getBrandInformation(BrandGrpcId request);
}
