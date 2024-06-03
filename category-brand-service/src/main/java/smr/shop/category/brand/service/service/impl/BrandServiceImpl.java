package smr.shop.category.brand.service.service.impl;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smr.shop.category.brand.service.dto.request.BrandCreateRequest;
import smr.shop.category.brand.service.dto.request.BrandUpdateRequest;
import smr.shop.category.brand.service.dto.response.BrandResponse;
import smr.shop.category.brand.service.exception.CategoryBrandServiceException;
import smr.shop.category.brand.service.mapper.CategoryBrandServiceMapper;
import smr.shop.category.brand.service.model.BrandEntity;
import smr.shop.category.brand.service.repository.BrandRepository;
import smr.shop.category.brand.service.service.BrandService;
import smr.shop.libs.common.constant.CacheConstants;
import smr.shop.libs.common.constant.MessagingConstants;
import smr.shop.libs.common.constant.ServiceConstants;
import smr.shop.libs.common.dto.message.BrandMessageModel;
import smr.shop.libs.common.dto.message.UploadMessageModel;
import smr.shop.libs.grpc.brand.BrandGrpcResponse;
import smr.shop.libs.grpc.client.UploadGrpcClient;
import smr.shop.libs.grpc.object.BrandGrpcId;
import smr.shop.libs.grpc.upload.UploadGrpcResponse;
import smr.shop.libs.outbox.service.OutboxService;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class BrandServiceImpl implements BrandService {
    // repository
    private final BrandRepository brandRepository;

    // service mapper
    private final CategoryBrandServiceMapper categoryBrandServiceMapper;

    private final OutboxService outboxService;

    //grpc clients
    private final UploadGrpcClient uploadGrpcClient;


    public BrandServiceImpl(BrandRepository brandRepository,
                            CategoryBrandServiceMapper categoryBrandServiceMapper,
                            OutboxService outboxService,
                            UploadGrpcClient uploadGrpcClient) {
        this.brandRepository = brandRepository;
        this.categoryBrandServiceMapper = categoryBrandServiceMapper;
        this.outboxService = outboxService;
        this.uploadGrpcClient = uploadGrpcClient;
    }

//    ----------------------------------- Create or Add -----------------------------------

    @Override
    @Transactional
    @CacheEvict(value = CacheConstants.BRAND_LIST, allEntries = true)
    public void createBrand(BrandCreateRequest request) {
        UploadGrpcResponse uploadGrpcResponse = uploadGrpcClient.getUploadById(request.getImageId().toString());

        BrandEntity brandEntity = categoryBrandServiceMapper.brandCreateResponseToBrandEntity(request);
        brandEntity.setImageUrl(uploadGrpcResponse.getUrl());
        brandEntity.setCreatedAt(ZonedDateTime.now(ServiceConstants.ZONE_ID));
        brandEntity.setUpdatedAt(ZonedDateTime.now(ServiceConstants.ZONE_ID));
        brandEntity = brandRepository.save(brandEntity);
        BrandResponse brandResponse = categoryBrandServiceMapper.brandEntityToBrandResponse(brandEntity);
        brandResponse.setImageUrl(uploadGrpcResponse.getUrl());
    }


//    ----------------------------------- Update -----------------------------------

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = CacheConstants.BRAND, key = "#id"),
            @CacheEvict(value = CacheConstants.BRAND_LIST, allEntries = true)
    })
    public void updateBrand(Long id, BrandUpdateRequest request) {
        BrandEntity brandEntity = findById(id);
        validateBrand(brandEntity);
        BrandEntity brandEntityUpdated = categoryBrandServiceMapper.brandUpdateRequestToBrandEntity(request, brandEntity);
        brandEntityUpdated.setUpdatedAt(ZonedDateTime.now(ServiceConstants.ZONE_ID));
        brandRepository.save(brandEntityUpdated);
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = CacheConstants.BRAND, key = "#id"),
            @CacheEvict(value = CacheConstants.BRAND_LIST, allEntries = true)
    })
    public void updateBrandImage(Long id, UUID imageId) {
        BrandEntity brandEntity = findById(id);
        validateBrand(brandEntity);

        UploadMessageModel uploadMessageModel =
                categoryBrandServiceMapper.brandEntityToUploadMessageModel(brandEntity);

        UploadGrpcResponse image = uploadGrpcClient.getUploadById(imageId.toString());
        brandEntity.setImageUrl(image.getUrl());
        brandEntity.setUpdatedAt(ZonedDateTime.now(ServiceConstants.ZONE_ID));
        brandRepository.save(brandEntity);
        outboxService.save(uploadMessageModel, MessagingConstants.IMAGE_DELETE_TOPIC);
    }

//    ----------------------------------- Delete -----------------------------------

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = CacheConstants.BRAND, key = "#id"),
            @CacheEvict(value = CacheConstants.BRAND_LIST, allEntries = true)
    })
    public void deleteBrand(Long id) {
        BrandEntity brandEntity = findById(id);
        brandEntity.setIsDeleted(Boolean.TRUE);
        brandEntity.setUpdatedAt(ZonedDateTime.now(ServiceConstants.ZONE_ID));
        brandRepository.save(brandEntity);
        BrandMessageModel brandMessageModel = categoryBrandServiceMapper.brandEntityToBrandDeleteMessageModel(brandEntity);
        outboxService.save(brandMessageModel, MessagingConstants.BRAND_DELETE_TOPIC);
    }

    @Override
    @Transactional
    @Caching(evict = {
            @CacheEvict(value = CacheConstants.BRAND, key = "#id"),
            @CacheEvict(value = CacheConstants.BRAND_LIST, allEntries = true)
    })
    public void deleteBrandImage(Long id) {
        BrandEntity brandEntity = findById(id);
        validateBrand(brandEntity);
        UploadMessageModel uploadMessageModel = categoryBrandServiceMapper.brandEntityToUploadMessageModel(brandEntity);
        brandEntity.setImageUrl(null);
        brandEntity.setUpdatedAt(ZonedDateTime.now(ServiceConstants.ZONE_ID));
        brandRepository.save(brandEntity);
        outboxService.save(uploadMessageModel, MessagingConstants.IMAGE_DELETE_TOPIC);
    }

//    ----------------------------------- Get -----------------------------------

    @Override
    @Cacheable(value = CacheConstants.BRAND_LIST, key = "#page", sync = true)
    public List<BrandResponse> getAllBrands(Integer page) {
        Pageable pageable = PageRequest.of(page, ServiceConstants.pageSize);
        return brandRepository.findAllByIsDeletedFalse(pageable).map(categoryBrandServiceMapper::brandEntityToBrandResponse).toList();
    }

    @Override
    @Transactional
    @Cacheable(value = CacheConstants.BRAND, key = "#id", sync = true)
    public BrandResponse getBrand(Long id) {
        BrandEntity brandEntity = findById(id);
        validateBrand(brandEntity);
        return categoryBrandServiceMapper.brandEntityToBrandResponse(brandEntity);
    }

    @Override
    @Cacheable(value = CacheConstants.BRAND, key = "#request.id", sync = true)
    public BrandGrpcResponse getBrandInformation(BrandGrpcId request) {
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
