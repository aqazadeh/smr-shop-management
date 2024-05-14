package smr.shop.shop.service.service;

import smr.shop.shop.service.dto.request.CreateShopRequest;
import smr.shop.shop.service.dto.request.UpdateShopAddressRequest;
import smr.shop.shop.service.dto.request.UpdateShopRequest;
import smr.shop.shop.service.dto.request.UpdateShopStatusRequest;
import smr.shop.shop.service.dto.response.ShopAddressResponse;
import smr.shop.shop.service.dto.response.ShopResponse;
import smr.shop.shop.service.model.Shop;

import java.util.List;

public interface ShopService {
    ShopResponse createShop(CreateShopRequest request);

    ShopResponse updateShop(Long id, UpdateShopRequest request);

    void deleteShop(Long id);

    List<ShopResponse> getAllShop(Integer page);

    ShopResponse getShopById(Long id);

    ShopResponse updateShopStatus(Long id, UpdateShopStatusRequest request);

    Shop findById(Long id);

    ShopAddressResponse updateShopAddress(Long id, UpdateShopAddressRequest request);

    ShopAddressResponse getShopAddressById(Long id, Long addressId);
}
