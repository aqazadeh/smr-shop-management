package smr.shop.brand.service.service;

import smr.shop.brand.service.dto.request.BrandCreateRequest;
import smr.shop.brand.service.dto.request.BrandUpdateRequest;
import smr.shop.brand.service.dto.response.BrandResponse;
import smr.shop.brand.service.model.BrandEntity;
import smr.shop.libs.grpc.brand.BrandGrpcRequest;
import smr.shop.libs.grpc.brand.BrandGrpcResponse;

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

    BrandGrpcResponse getBrandInformation(BrandGrpcRequest request);
}
