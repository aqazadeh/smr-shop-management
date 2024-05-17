package smr.shop.brand.service.service.impl;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smr.shop.brand.service.dto.request.BrandCreateRequest;
import smr.shop.brand.service.dto.request.BrandUpdateRequest;
import smr.shop.brand.service.dto.response.BrandResponse;
import smr.shop.brand.service.exception.CategoryBrandServiceException;
import smr.shop.brand.service.grpc.client.BrandGrpcClientService;
import smr.shop.brand.service.mapper.CategoryBrandServiceMapper;
import smr.shop.brand.service.messaging.publisher.BrandDeleteMessagePublisher;
import smr.shop.brand.service.messaging.publisher.ImageDeleteMessagePublisher;
import smr.shop.brand.service.model.BrandEntity;
import smr.shop.brand.service.repository.BrandRepository;
import smr.shop.brand.service.service.BrandService;
import smr.shop.libs.common.constant.ServiceConstants;
import smr.shop.libs.common.dto.message.BrandDeleteMessageModel;
import smr.shop.libs.common.dto.message.BrandImageDeleteMessageModel;
import smr.shop.libs.grpc.brand.BrandGrpcRequest;
import smr.shop.libs.grpc.brand.BrandGrpcResponse;
import smr.shop.libs.grpc.upload.UploadGrpcResponse;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class BrandServiceImpl implements BrandService {
    private final BrandRepository brandRepository;
    private final CategoryBrandServiceMapper categoryBrandServiceMapper;
    private final BrandGrpcClientService brandGrpcClientService;
    private final ImageDeleteMessagePublisher imageDeleteMessagePublisher;
    private final BrandDeleteMessagePublisher brandDeleteMessagePublisher;

    public BrandServiceImpl(BrandRepository brandRepository,
                            CategoryBrandServiceMapper categoryBrandServiceMapper,
                            BrandGrpcClientService brandGrpcClientService,
                            ImageDeleteMessagePublisher imageDeleteMessagePublisher, BrandDeleteMessagePublisher brandDeleteMessagePublisher) {
        this.brandRepository = brandRepository;
        this.categoryBrandServiceMapper = categoryBrandServiceMapper;
        this.brandGrpcClientService = brandGrpcClientService;
        this.imageDeleteMessagePublisher = imageDeleteMessagePublisher;
        this.brandDeleteMessagePublisher = brandDeleteMessagePublisher;
    }

//    ----------------------------------- Create or Add -----------------------------------

    @Override
    @Transactional
    public void createBrand(BrandCreateRequest request) {
        UploadGrpcResponse image = brandGrpcClientService.getImage(request.getImageId().toString());

        BrandEntity brandEntity = categoryBrandServiceMapper.brandCreateResponseToBrandEntity(request);
        brandEntity.setImageUrl(image.getUrl());
        brandEntity.setCreatedAt(ZonedDateTime.now(ServiceConstants.ZONE_ID));
        brandEntity.setUpdatedAt(ZonedDateTime.now(ServiceConstants.ZONE_ID));
        brandEntity = brandRepository.save(brandEntity);

        BrandResponse brandResponse = categoryBrandServiceMapper.brandEntityToBrandResponse(brandEntity);
        brandResponse.setImageUrl(image.getUrl());
    }


//    ----------------------------------- Update -----------------------------------

    @Override
    @Transactional
    public void updateBrand(Long id, BrandUpdateRequest request) {
        BrandEntity brandEntity = findById(id);
        validateBrand(brandEntity);
        BrandEntity brandEntityUpdated = categoryBrandServiceMapper.brandUpdateRequestToBrandEntity(request, brandEntity);
        brandEntityUpdated.setUpdatedAt(ZonedDateTime.now(ServiceConstants.ZONE_ID));
        brandRepository.save(brandEntityUpdated);
    }

    @Override
    @Transactional
    public void updateBrandImage(Long id, UUID imageId) {
        BrandEntity brandEntity = findById(id);
        validateBrand(brandEntity);

        BrandImageDeleteMessageModel brandImageDeleteMessageModel =
                categoryBrandServiceMapper.brandEntityToBrandImageDeleteMessageModel(brandEntity);

        UploadGrpcResponse image = brandGrpcClientService.getImage(imageId.toString());
        brandEntity.setImageUrl(image.getUrl());
        brandEntity.setUpdatedAt(ZonedDateTime.now(ServiceConstants.ZONE_ID));
        brandRepository.save(brandEntity);
        imageDeleteMessagePublisher.publish(brandImageDeleteMessageModel);
    }

//    ----------------------------------- Delete -----------------------------------

    @Override
    @Transactional
    public void deleteBrand(Long id) {
        BrandEntity brandEntity = findById(id);
        brandEntity.setIsDeleted(Boolean.TRUE);
        brandEntity.setUpdatedAt(ZonedDateTime.now(ServiceConstants.ZONE_ID));
        brandRepository.save(brandEntity);
        BrandDeleteMessageModel brandDeleteMessageModel = categoryBrandServiceMapper.brandEntityToBrandDeleteMessageModel(brandEntity);
        brandDeleteMessagePublisher.publish(brandDeleteMessageModel);
    }

    @Override
    @Transactional
    public void deleteBrandImage(Long id) {
        BrandEntity brandEntity = findById(id);
        validateBrand(brandEntity);
        BrandImageDeleteMessageModel brandImageDeleteMessageModel =
                categoryBrandServiceMapper.brandEntityToBrandImageDeleteMessageModel(brandEntity);
        brandEntity.setImageUrl(null);
        brandEntity.setUpdatedAt(ZonedDateTime.now(ServiceConstants.ZONE_ID));
        brandRepository.save(brandEntity);
        imageDeleteMessagePublisher.publish(brandImageDeleteMessageModel);
    }

//    ----------------------------------- Get -----------------------------------

    @Override
    public List<BrandResponse> getAllBrands(Integer page) {
        Pageable pageable = PageRequest.of(page, ServiceConstants.pageSize);
        return brandRepository.findAllByIsDeletedFalse(pageable).map(categoryBrandServiceMapper::brandEntityToBrandResponse).toList();
    }

    @Override
    @Transactional
    public BrandResponse getBrand(Long id) {
        BrandEntity brandEntity = findById(id);
        validateBrand(brandEntity);
        return categoryBrandServiceMapper.brandEntityToBrandResponse(brandEntity);
    }

    @Override
    public BrandGrpcResponse getBrandInformation(BrandGrpcRequest request) {
        BrandEntity brandEntity = findById(request.getId());
        return categoryBrandServiceMapper.brandEntityToBrandGrpcResponse(brandEntity);
    }

//    ----------------------------------- Extra -----------------------------------

    @Override
    public BrandEntity findById(Long id) {
        return brandRepository.findById(id).orElseThrow(() -> new CategoryBrandServiceException("Brand Not found With id : " + id, HttpStatus.NOT_FOUND));
    }

    private void validateBrand(BrandEntity brandEntity) {
        if (brandEntity.getIsDeleted().equals(Boolean.TRUE)) {
            throw new CategoryBrandServiceException("Brand is deleted with id : " + brandEntity.getId(), HttpStatus.BAD_REQUEST);
        }
    }

}
