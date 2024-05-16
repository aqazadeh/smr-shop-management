package smr.shop.shop.service.service.impl;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smr.shop.libs.common.constant.ServiceConstants;
import smr.shop.libs.common.helper.UserHelper;
import smr.shop.libs.grpc.upload.UploadGrpcResponse;
import smr.shop.shop.service.dto.request.CreateShopRequest;
import smr.shop.shop.service.dto.request.UpdateShopAddressRequest;
import smr.shop.shop.service.dto.request.UpdateShopRequest;
import smr.shop.shop.service.dto.response.ShopAddressResponse;
import smr.shop.shop.service.dto.response.ShopResponse;
import smr.shop.shop.service.exception.ShopException;
import smr.shop.shop.service.grpc.client.UploadGrpcServiceClient;
import smr.shop.shop.service.mapper.ShopServiceMapper;
import smr.shop.shop.service.model.ShopEntity;
import smr.shop.shop.service.model.valueobject.ShopAddress;
import smr.shop.shop.service.model.valueobject.ShopStatus;
import smr.shop.shop.service.repository.ShopRepository;
import smr.shop.shop.service.service.ShopService;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class ShopServiceImpl implements ShopService {

    private final ShopServiceMapper shopServiceMapper;
    private final ShopRepository shopRepository;
    private final UploadGrpcServiceClient uploadGrpcServiceClient;

    public ShopServiceImpl(ShopServiceMapper shopServiceMapper,
                           ShopRepository shopRepository,
                           UploadGrpcServiceClient uploadGrpcServiceClient) {
        this.shopServiceMapper = shopServiceMapper;
        this.shopRepository = shopRepository;
        this.uploadGrpcServiceClient = uploadGrpcServiceClient;
    }

    @Override
    @Transactional
    public void createShop(CreateShopRequest request) {
        UploadGrpcResponse image = uploadGrpcServiceClient.getImageById(request.getImageId().toString());
        UUID userId = UserHelper.getUserId();
        ShopEntity shopEntity = shopServiceMapper.createShopRequestToShopEntity(request);
        shopEntity.setLogo(image.getUrl());
        shopEntity.setUserId(userId);
        shopEntity.setCreatedAt(ZonedDateTime.now(ServiceConstants.ZONE_ID));
        shopEntity.setUpdatedAt(ZonedDateTime.now(ServiceConstants.ZONE_ID));
        shopRepository.save(shopEntity);
    }

    @Override
    @Transactional
    public void updateShop(Long shopId, UpdateShopRequest request) {
        ShopEntity shopEntity = findById(shopId);
        validateShopCreator(shopEntity);
        ShopEntity updateShopEntity = shopServiceMapper.updateShopRequestToShopEntity(request, shopEntity);
        shopEntity.setUpdatedAt(ZonedDateTime.now(ServiceConstants.ZONE_ID));
        shopRepository.save(updateShopEntity);
    }

    @Override
    @Transactional
    public void deleteShop(Long shopId) {
        ShopEntity shopEntity = findById(shopId);
        validateShopCreator(shopEntity);
        shopEntity.setUpdatedAt(ZonedDateTime.now(ServiceConstants.ZONE_ID));
        shopEntity.setStatus(ShopStatus.CLOSED);
        shopRepository.save(shopEntity);
        //TODO send Kafka event
    }

    @Override
    public void deleteShopImage(Long shopId) {
        ShopEntity shopEntity = findById(shopId);
        validateShopCreator(shopEntity);
        shopEntity.setLogo(null);
        shopEntity.setUpdatedAt(ZonedDateTime.now(ServiceConstants.ZONE_ID));
        shopRepository.save(shopEntity);
        //TODO send kafka event to image deleted!
    }

    @Override
    @Transactional
    public void updateShopStatus(Long shopId, ShopStatus status) {
        ShopEntity shopEntity = findById(shopId);
        shopEntity.setStatus(status);
        shopEntity.setUpdatedAt(ZonedDateTime.now(ServiceConstants.ZONE_ID));
        shopRepository.save(shopEntity);
        //TODO send Kafka status
    }

    @Override
    public void UpdateShopLogo(Long shopId, UUID imageId) {
        ShopEntity shopEntity = findById(shopId);
        validateShopCreator(shopEntity);
        UploadGrpcResponse image = uploadGrpcServiceClient.getImageById(imageId.toString());
        shopEntity.setLogo(image.getUrl());
        shopEntity.setUpdatedAt(ZonedDateTime.now(ServiceConstants.ZONE_ID));
        shopRepository.save(shopEntity);
        // TODO send kafka event to Image updated
    }

    @Override
    @Transactional
    public void updateShopAddress(Long shopId, UpdateShopAddressRequest request) {
        ShopEntity shopEntity = findById(shopId);
        validateShopCreator(shopEntity);
        ShopAddress shopAddress = shopServiceMapper.updateShopAddressRequestToShopAddress(request);
        shopEntity.setAddress(shopAddress);
        shopEntity.setUpdatedAt(ZonedDateTime.now(ServiceConstants.ZONE_ID));
        shopRepository.save(shopEntity);
    }

    @Override
    public ShopAddressResponse getShopAddress(Long shopId) {
        ShopEntity shopEntity = findById(shopId);
        ShopAddress address = shopEntity.getAddress();
        return shopServiceMapper.shopAddressToShopAddressResponse(address);
    }

    @Override
    public ShopResponse getShopById(Long shopId) {
        ShopEntity shopEntity = findById(shopId);
        return shopServiceMapper.shopEntitytoShopResponse(shopEntity);
    }

    @Override
    public List<ShopResponse> getAllShop(Integer page) {
        Pageable pageable = PageRequest.of(page, ServiceConstants.pageSize);
        return shopRepository.findAll(pageable).map(shopServiceMapper::shopEntitytoShopResponse).toList();
    }

    @Override
    public ShopEntity findById(Long shopId) {
        return shopRepository.findById(shopId)
                .orElseThrow(() -> new ShopException("Shop Not Found With Id:" + shopId, HttpStatus.NOT_FOUND));
    }

    private void validateShopCreator(ShopEntity shopEntity) {
        UUID userId = UserHelper.getUserId();
        if (!shopEntity.getUserId().equals(userId)) {
            throw new ShopException("you dont have access to manipulate shop with id: " + shopEntity.getId(), HttpStatus.BAD_REQUEST);
        }
    }

}
