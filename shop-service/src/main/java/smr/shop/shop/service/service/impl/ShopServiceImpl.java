package smr.shop.shop.service.service.impl;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smr.shop.libs.common.constant.ServiceConstants;
import smr.shop.libs.common.dto.message.ShopMessageModel;
import smr.shop.libs.common.dto.message.UploadMessageModel;
import smr.shop.libs.common.helper.UserHelper;
import smr.shop.libs.grpc.client.UploadGrpcClient;
import smr.shop.libs.grpc.object.ShopGrpcId;
import smr.shop.libs.grpc.object.UserGrpcId;
import smr.shop.libs.grpc.product.shop.ShopGrpcResponse;
import smr.shop.libs.grpc.upload.UploadGrpcResponse;
import smr.shop.shop.service.dto.request.CreateShopRequest;
import smr.shop.shop.service.dto.request.UpdateShopAddressRequest;
import smr.shop.shop.service.dto.request.UpdateShopRequest;
import smr.shop.shop.service.dto.response.ShopAddressResponse;
import smr.shop.shop.service.dto.response.ShopResponse;
import smr.shop.shop.service.exception.ShopServiceException;
import smr.shop.shop.service.mapper.ShopServiceMapper;
import smr.shop.shop.service.message.publisher.ImageDeleteMessagePublisher;
import smr.shop.shop.service.message.publisher.ShopStatusChangeMessagePublisher;
import smr.shop.shop.service.model.ShopEntity;
import smr.shop.shop.service.model.valueobject.ShopAddress;
import smr.shop.shop.service.model.valueobject.ShopStatus;
import smr.shop.shop.service.repository.ShopRepository;
import smr.shop.shop.service.service.ShopService;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ShopServiceImpl implements ShopService {
    // repository
    private final ShopRepository shopRepository;

    // service mapper
    private final ShopServiceMapper shopServiceMapper;

    // rpc
    private final UploadGrpcClient uploadGrpcClient;

    // message Broker
    private final ShopStatusChangeMessagePublisher shopStatusChangeMessagePublisher;
    private final ImageDeleteMessagePublisher imageDeleteMessagePublisher;

    public ShopServiceImpl(ShopRepository shopRepository,
                           ShopServiceMapper shopServiceMapper,
                           UploadGrpcClient uploadGrpcClient,
                           ShopStatusChangeMessagePublisher shopStatusChangeMessagePublisher,
                           ImageDeleteMessagePublisher imageDeleteMessagePublisher) {
        this.shopRepository = shopRepository;
        this.shopServiceMapper = shopServiceMapper;
        this.uploadGrpcClient = uploadGrpcClient;
        this.shopStatusChangeMessagePublisher = shopStatusChangeMessagePublisher;
        this.imageDeleteMessagePublisher = imageDeleteMessagePublisher;
    }


//    ----------------------------------- Create or Add -----------------------------------

    @Override
    @Transactional
    public void createShop(CreateShopRequest request) {
        UploadGrpcResponse image = uploadGrpcClient.getUploadById(request.getImageId().toString());
        UUID userId = UserHelper.getUserId();
        if (shopRepository.findByUserId(userId).isPresent()) {
            throw new ShopServiceException("you  already have a shop", HttpStatus.BAD_REQUEST);
        }
        ShopEntity shopEntity = shopServiceMapper.createShopRequestToShopEntity(request);
        shopEntity.setLogo(image.getUrl());
        shopEntity.setUserId(userId);
        shopEntity.setCreatedAt(ZonedDateTime.now(ServiceConstants.ZONE_ID));
        shopEntity.setUpdatedAt(ZonedDateTime.now(ServiceConstants.ZONE_ID));
        shopRepository.save(shopEntity);
    }

//    ----------------------------------- Update -----------------------------------

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
    public void updateShopStatus(Long shopId, ShopStatus status) {
        ShopEntity shopEntity = findById(shopId);
        shopEntity.setStatus(status);
        shopEntity.setUpdatedAt(ZonedDateTime.now(ServiceConstants.ZONE_ID));
        ShopEntity savedShopEntity = shopRepository.save(shopEntity);
        ShopMessageModel shopMessageModel = shopServiceMapper.shopEntityToShopMessageModel(savedShopEntity);
        shopStatusChangeMessagePublisher.publish(shopMessageModel);
    }

    @Override
    @Transactional
    public void updateShopLogo(Long shopId, UUID imageId) {
        ShopEntity shopEntity = findById(shopId);
        String oldImage = shopEntity.getLogo();
        validateShopCreator(shopEntity);
        UploadGrpcResponse image = uploadGrpcClient.getUploadById(imageId.toString());
        shopEntity.setLogo(image.getUrl());
        shopEntity.setUpdatedAt(ZonedDateTime.now(ServiceConstants.ZONE_ID));
        shopRepository.save(shopEntity);
        UploadMessageModel uploadMessageModel = shopServiceMapper.shopEntityToUploadMessageModel(oldImage);
        imageDeleteMessagePublisher.publish(uploadMessageModel);
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

//    ----------------------------------- Delete -----------------------------------

    @Override
    @Transactional
    public void deleteShop(Long shopId) {
        ShopEntity shopEntity = findById(shopId);
        validateShopCreator(shopEntity);
        if (shopEntity.getStatus() != ShopStatus.CONFIRMED) {
            throw new ShopServiceException("your is shop not a active", HttpStatus.BAD_REQUEST);
        }
        shopEntity.setStatus(ShopStatus.CLOSED);
        shopEntity.setUpdatedAt(ZonedDateTime.now(ServiceConstants.ZONE_ID));
        shopRepository.save(shopEntity);
        ShopEntity savedShopEntity = shopRepository.save(shopEntity);
        ShopMessageModel shopMessageModel = shopServiceMapper.shopEntityToShopMessageModel(savedShopEntity);
        shopStatusChangeMessagePublisher.publish(shopMessageModel);
    }

    @Override
    @Transactional
    public void deleteShopImage(Long shopId) {
        ShopEntity shopEntity = findById(shopId);
        if (shopEntity.getLogo() == null) return;
        validateShopCreator(shopEntity);
        shopEntity.setLogo(null);
        shopEntity.setUpdatedAt(ZonedDateTime.now(ServiceConstants.ZONE_ID));
        ShopEntity savedShopEntity = shopRepository.save(shopEntity);
        UploadMessageModel uploadMessageModel = shopServiceMapper.shopEntityToUploadMessageModel(savedShopEntity.getLogo());
        imageDeleteMessagePublisher.publish(uploadMessageModel);
    }

//    ----------------------------------- Get -----------------------------------

    @Override
    public ShopAddressResponse getShopAddress(Long shopId) {
        ShopEntity shopEntity = findById(shopId);
        ShopAddress address = shopEntity.getAddress();
        return shopServiceMapper.shopAddressToShopAddressResponse(address);
    }

    @Override
    public ShopGrpcResponse getShopInformationByShopId(ShopGrpcId request) {
        ShopEntity shopEntity = findById(request.getId());
        return shopServiceMapper.shopEntityToShopGrpcResponse(shopEntity);
    }

    @Override
    public ShopGrpcResponse getShopInformationByUserId(UserGrpcId request) {
        UUID userId = UUID.fromString(request.getId());
        Optional<ShopEntity> optionalShopEntity = shopRepository.findByUserId(userId);
        if (optionalShopEntity.isEmpty()) {
            throw new ShopServiceException("cannot to find shop with userId: " + userId, HttpStatus.BAD_REQUEST);
        }
        ShopEntity shopEntity = optionalShopEntity.get();
        return shopServiceMapper.shopEntityToShopGrpcResponse(shopEntity);
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

//    ----------------------------------- Extra -----------------------------------

    @Override
    public ShopEntity findById(Long shopId) {
        return shopRepository.findById(shopId)
                .orElseThrow(() -> new ShopServiceException("Shop Not Found With Id:" + shopId, HttpStatus.NOT_FOUND));
    }

    private void validateShopCreator(ShopEntity shopEntity) {
        UUID userId = UserHelper.getUserId();
        if (!shopEntity.getUserId().equals(userId)) {
            throw new ShopServiceException("you dont have access to manipulate shop with id: " + shopEntity.getId(), HttpStatus.BAD_REQUEST);
        }
    }

}
