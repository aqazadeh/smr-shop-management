package smr.shop.shop.service.service;

import smr.shop.libs.grpc.object.ShopGrpcId;
import smr.shop.libs.grpc.object.UserGrpcId;
import smr.shop.libs.grpc.product.shop.ShopGrpcResponse;
import smr.shop.shop.service.dto.request.CreateShopRequest;
import smr.shop.shop.service.dto.request.UpdateShopAddressRequest;
import smr.shop.shop.service.dto.request.UpdateShopRequest;
import smr.shop.shop.service.dto.response.ShopAddressResponse;
import smr.shop.shop.service.dto.response.ShopResponse;
import smr.shop.shop.service.model.ShopEntity;
import smr.shop.shop.service.model.valueobject.ShopStatus;

import java.util.List;
import java.util.UUID;

public interface ShopService {
    void createShop(CreateShopRequest request);

    void updateShop(Long shopId, UpdateShopRequest request);

    void updateShopStatus(Long shopId, ShopStatus status);

    void updateShopLogo(Long shopId, UUID imageId);

    void updateShopAddress(Long id, UpdateShopAddressRequest request);

    void deleteShop(Long shopId);

    void deleteShopImage(Long shopId);

    List<ShopResponse> getAllShop(Integer page);

    ShopResponse getShopById(Long shopId);

    ShopEntity findById(Long shopId);

    ShopAddressResponse getShopAddress(Long shopId);

    ShopGrpcResponse getShopInformationByShopId(ShopGrpcId request);

    ShopGrpcResponse getShopInformationByUserId(UserGrpcId request);
}
