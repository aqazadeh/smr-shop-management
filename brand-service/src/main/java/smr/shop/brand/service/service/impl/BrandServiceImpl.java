package smr.shop.brand.service.service.impl;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import smr.shop.brand.service.dto.request.BrandCreateRequest;
import smr.shop.brand.service.dto.request.BrandUpdateRequest;
import smr.shop.brand.service.dto.response.BrandResponse;
import smr.shop.brand.service.exception.BrandException;
import smr.shop.brand.service.grpc.BrandGrpcClientService;
import smr.shop.brand.service.mapper.BrandServiceMapper;
import smr.shop.brand.service.messaging.publisher.BrandDeleteMessagePublisher;
import smr.shop.brand.service.messaging.publisher.BrandImageDeleteMessagePublisher;
import smr.shop.brand.service.model.BrandEntity;
import smr.shop.brand.service.repository.BrandRepository;
import smr.shop.brand.service.service.BrandService;
import smr.shop.libs.common.constant.ServiceConstants;
import smr.shop.libs.common.dto.message.BrandDeleteMessageModel;
import smr.shop.libs.common.dto.message.BrandImageDeleteMessageModel;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class BrandServiceImpl implements BrandService {
    private final BrandRepository brandRepository;
    private final BrandServiceMapper brandServiceMapper;
    private final BrandGrpcClientService brandGrpcClientService;
    private final BrandImageDeleteMessagePublisher brandImageDeleteMessagePublisher;
    private final BrandDeleteMessagePublisher brandDeleteMessagePublisher;

    public BrandServiceImpl(BrandRepository brandRepository,
                            BrandServiceMapper brandServiceMapper,
                            BrandGrpcClientService brandGrpcClientService,
                            BrandImageDeleteMessagePublisher brandImageDeleteMessagePublisher, BrandDeleteMessagePublisher brandDeleteMessagePublisher) {
        this.brandRepository = brandRepository;
        this.brandServiceMapper = brandServiceMapper;
        this.brandGrpcClientService = brandGrpcClientService;
        this.brandImageDeleteMessagePublisher = brandImageDeleteMessagePublisher;
        this.brandDeleteMessagePublisher = brandDeleteMessagePublisher;
    }

    @Override
    public BrandResponse create(BrandCreateRequest request) {
        UploadGrpcResponse image = brandGrpcClientService.getImage(request.getImageId().toString());

        BrandEntity brandEntity = brandServiceMapper.brandCreateResponseToBrandEntity(request);
        brandEntity.setImageId(image.getId());
        brandEntity.setCreatedAt(ZonedDateTime.of(LocalDateTime.now(), ZoneId.of(ServiceConstants.UTC)));
        brandEntity.setUpdatedAt(ZonedDateTime.of(LocalDateTime.now(), ZoneId.of(ServiceConstants.UTC)));
        brandEntity = brandRepository.save(brandEntity);

        BrandResponse brandResponse = brandServiceMapper.brandEntityToBrandResponse(brandEntity);
        brandResponse.setImageUrl(image.getUrl());
        return brandResponse;
    }

    @Override
    public BrandResponse updateBrand(Long id, BrandUpdateRequest request) {
        BrandEntity brandEntity = findById(id);

        BrandEntity brandEntityUpdated = brandServiceMapper.brandUpdateRequestToBrandEntity(request, brandEntity);
        brandEntityUpdated.setUpdatedAt(ZonedDateTime.of(LocalDateTime.now(), ZoneId.of(ServiceConstants.UTC)));
        brandEntity = brandRepository.save(brandEntityUpdated);

        UploadGrpcResponse image = brandGrpcClientService.getImage(brandEntity.getImageId());
        BrandResponse brandResponse = brandServiceMapper.brandEntityToBrandResponse(brandEntity);
        brandResponse.setImageUrl(image.getUrl());
        return brandResponse;
    }

    @Override
    public void deleteBrand(Long id) {
        BrandEntity brandEntity = findById(id);
        brandRepository.delete(brandEntity);
        BrandDeleteMessageModel brandDeleteMessageModel = brandServiceMapper.brandEntityToBrandDeleteMessageModel(brandEntity);
        brandDeleteMessagePublisher.publish(brandDeleteMessageModel);
    }

    @Override
    public void updateBrandImage(Long id, UUID imageId) {
        BrandEntity brandEntity = findById(id);

        BrandImageDeleteMessageModel brandImageDeleteMessageModel =
                brandServiceMapper.brandEntityToBrandImageDeleteMessageModel(brandEntity);

        UploadGrpcResponse image = brandGrpcClientService.getImage(imageId.toString());
        brandEntity.setImageId(image.getId());
        brandEntity.setUpdatedAt(ZonedDateTime.of(LocalDateTime.now(), ZoneId.of(ServiceConstants.UTC)));
        brandRepository.save(brandEntity);
        brandImageDeleteMessagePublisher.publish(brandImageDeleteMessageModel);
    }

    @Override
    public void deleteBrandImage(Long id) {
        BrandEntity brandEntity = findById(id);
        BrandImageDeleteMessageModel brandImageDeleteMessageModel =
                brandServiceMapper.brandEntityToBrandImageDeleteMessageModel(brandEntity);
        brandEntity.setImageId("");
        brandEntity.setUpdatedAt(ZonedDateTime.of(LocalDateTime.now(), ZoneId.of(ServiceConstants.UTC)));
        brandRepository.save(brandEntity);
        brandImageDeleteMessagePublisher.publish(brandImageDeleteMessageModel);
    }

    @Override
    public List<BrandResponse> getAllBrands(Integer page) {
        Pageable pageable = PageRequest.of(page, ServiceConstants.pageSize);
        List<BrandEntity> list = brandRepository.findAll(pageable).toList();
        //TODO fix many grpc request problem
        List<BrandResponse> brandResponseList = list.stream().map(entity -> {
            BrandResponse brandResponse = brandServiceMapper.brandEntityToBrandResponse(entity);
            UploadGrpcResponse image = brandGrpcClientService.getImage(entity.getImageId());
            brandResponse.setImageUrl(image.getUrl());
            return brandResponse;
        }).toList();

        return brandResponseList;
    }

    @Override
    public BrandResponse getBrand(Long id) {
        BrandEntity brandEntity = findById(id);
        UploadGrpcResponse image = brandGrpcClientService.getImage(brandEntity.getImageId());

        BrandResponse brandResponse = brandServiceMapper.brandEntityToBrandResponse(brandEntity);
        brandResponse.setImageUrl(image.getUrl());
        return brandResponse;
    }

    @Override
    public BrandEntity findById(Long id) {
        return brandRepository.findById(id).orElseThrow(() -> new BrandException("Brand Not found With id : " + id, HttpStatus.NOT_FOUND));
    }
}
