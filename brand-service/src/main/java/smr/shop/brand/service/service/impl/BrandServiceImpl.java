package smr.shop.brand.service.service.impl;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import smr.shop.brand.service.dto.request.BrandCreateRequest;
import smr.shop.brand.service.dto.request.BrandUpdateRequest;
import smr.shop.brand.service.dto.response.BrandResponse;
import smr.shop.brand.service.exception.BrandException;
import smr.shop.brand.service.mapper.BrandMapper;
import smr.shop.brand.service.model.BrandEntity;
import smr.shop.brand.service.repository.BrandRepository;
import smr.shop.brand.service.service.BrandService;
import smr.shop.libs.common.constant.ServiceConstants;

import java.util.List;
import java.util.UUID;

@Service
public class BrandServiceImpl implements BrandService {
    private final BrandRepository brandRepository;
    private final BrandMapper brandMapper;

    public BrandServiceImpl(BrandRepository brandRepository, BrandMapper brandMapper) {
        this.brandRepository = brandRepository;
        this.brandMapper = brandMapper;
    }

    @Override
    public BrandResponse create(BrandCreateRequest request) {
        BrandEntity brandEntity = brandMapper.brandCreateResponseToBrandEntity(request);
//        request.getImageId()  check ad add brandEntity
        brandEntity = brandRepository.save(brandEntity);

        return brandMapper.brandEntityToBrandResponse(brandEntity);
    }

    @Override
    public BrandResponse updateBrand(Long id, BrandUpdateRequest request) {
        BrandEntity brandEntity = findById(id);
        BrandEntity brandEntityUpdated = brandMapper.brandUpdateRequestToBrandEntity(request, brandEntity);
        brandEntity = brandRepository.save(brandEntityUpdated);
        return brandMapper.brandEntityToBrandResponse(brandEntity);
    }

    @Override
    public void deleteBrand(Long id) {
        BrandEntity brandEntity = findById(id);
        //send kafka event
        brandRepository.delete(brandEntity);
    }

    @Override
    public BrandResponse updateBrandImage(Long id, UUID imageId) {
        BrandEntity brandEntity = findById(id);
        //send kafka event
        // grpc find image has
        brandEntity.setImageId(imageId.toString());
        brandEntity = brandRepository.save(brandEntity);
        return brandMapper.brandEntityToBrandResponse(brandEntity);
    }

    @Override
    public void deleteBrandImage(Long id, UUID imageId) {
        BrandEntity brandEntity = findById(id);
        //send kafka event Image delete
        brandEntity.setImageId("");
        brandEntity.setImageId(imageId.toString());
        brandRepository.save(brandEntity);
    }

    @Override
    public List<BrandResponse> getAllBrands(Integer page) {
        Pageable pageable = PageRequest.of(page, ServiceConstants.pageSize);
        return brandRepository.findAll(pageable).map(brandMapper::brandEntityToBrandResponse).toList();
    }

    @Override
    public BrandResponse getBrand(Long id) {
        BrandEntity brandEntity = findById(id);
        return brandMapper.brandEntityToBrandResponse(brandEntity);
    }

    @Override
    public BrandEntity findById(Long id) {
        return brandRepository.findById(id).orElseThrow(() -> new BrandException("Brand Not found With id : " + id, HttpStatus.NOT_FOUND));
    }
}
