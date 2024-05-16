package smr.shop.shop.service.mapper;

import org.springframework.stereotype.Component;
import smr.shop.shop.service.dto.request.CreateShopRequest;
import smr.shop.shop.service.dto.request.UpdateShopAddressRequest;
import smr.shop.shop.service.dto.request.UpdateShopRequest;
import smr.shop.shop.service.dto.response.ShopAddressResponse;
import smr.shop.shop.service.dto.response.ShopResponse;
import smr.shop.shop.service.model.ShopEntity;
import smr.shop.shop.service.model.valueobject.ShopAddress;

@Component
public class ShopServiceMapper {
    public ShopEntity createShopRequestToShopEntity(CreateShopRequest request) {
        return ShopEntity.builder()
                .shopName(request.getName())
                .slug(request.getSlug())
                .phone(request.getPhone())
                .address(request.getAddress())
                .description(request.getDescription())
                .build();
    }

    public ShopEntity updateShopRequestToShopEntity(UpdateShopRequest request, ShopEntity shopEntity) {
        shopEntity.setShopName(request.getName());
        shopEntity.setSlug(request.getSlug());
        shopEntity.setDescription(request.getDescription());
        shopEntity.setPhone(request.getPhone());
        return shopEntity;
    }

    public ShopResponse shopEntitytoShopResponse(ShopEntity shopEntity) {
        return ShopResponse.builder()
                .id(shopEntity.getId())
                .name(shopEntity.getShopName())
                .slug(shopEntity.getSlug())
                .description(shopEntity.getDescription())
                .logo(shopEntity.getLogo())
                .phone(shopEntity.getPhone())
                .address(shopEntity.getAddress())
                .rating(shopEntity.getRating())
                .reviewsCount(shopEntity.getReviewsCount())
                .salesCount(shopEntity.getSalesCount())
                .viewCount(shopEntity.getViewCount())
                .status(shopEntity.getStatus())
                .build();
    }

    public ShopAddressResponse shopAddressToShopAddressResponse(ShopAddress shopAddress) {
        return ShopAddressResponse.builder()
                .name(shopAddress.getName())
                .street(shopAddress.getStreet())
                .city(shopAddress.getCity())
                .state(shopAddress.getState())
                .longitude(shopAddress.getLongitude())
                .latitude(shopAddress.getLatitude())
                .build();
    }

    public ShopAddress updateShopAddressRequestToShopAddress(UpdateShopAddressRequest request) {
        ShopAddress shopAddress = new ShopAddress();
        if (request.getName() != null) shopAddress.setName(request.getName());
        if (request.getStreet() != null) shopAddress.setStreet(request.getStreet());
        if (request.getCity() != null) shopAddress.setCity(request.getCity());
        if (request.getStreet() != null) shopAddress.setState(request.getState());
        if (request.getLongitude() != null) shopAddress.setLongitude(request.getLongitude());
        if (request.getLatitude() != null) shopAddress.setLatitude(request.getLatitude());
        return shopAddress;
    }
}
