package smr.shop.shop.service.mapper;

import org.springframework.stereotype.Component;
import smr.shop.shop.service.dto.request.CreateShopRequest;
import smr.shop.shop.service.dto.request.UpdateShopAddressRequest;
import smr.shop.shop.service.dto.request.UpdateShopRequest;
import smr.shop.shop.service.dto.response.ShopAddressResponse;
import smr.shop.shop.service.dto.response.ShopResponse;
import smr.shop.shop.service.model.Shop;
import smr.shop.shop.service.model.valueobject.ShopAddress;

@Component
public class ShopMapper {
    public Shop toShop(CreateShopRequest request) {
        Shop.ShopBuilder builder = Shop.builder();
        builder.userId(request.getUserId());
        builder.name(request.getName());
        builder.slug(request.getSlug());
        builder.logo(request.getLogo());
        builder.phone(request.getPhone());
        builder.address(request.getAddress());
        builder.description(request.getDescription());
        return builder.build();
    }

    public Shop toUpdateShop(UpdateShopRequest request, Shop shop) {
        shop.setUserId(request.getUserId());
        shop.setName(request.getName());
        shop.setSlug(request.getSlug());
        shop.setDescription(request.getDescription());
        shop.setLogo(request.getLogo());
        shop.setPhone(request.getPhone());
        shop.setAddress(request.getAddress());
        return shop;
    }

    public ShopResponse toShopResponse(Shop shop) {
        return ShopResponse.builder()
                .id(shop.getId())
                .userId(shop.getUserId())
                .name(shop.getName())
                .slug(shop.getSlug())
                .description(shop.getDescription())
                .logo(shop.getLogo())
                .phone(shop.getPhone())
                .address(shop.getAddress())
                .rating(shop.getRating())
                .reviewsCount(shop.getReviewsCount())
                .salesCount(shop.getSalesCount())
                .viewCount(shop.getViewCount())
                .status(shop.getStatus())
                .build();
    }

    public ShopAddressResponse toShopAddressResponse(ShopAddress shopAddress) {
        return ShopAddressResponse.builder()
                .name(shopAddress.getName())
                .street(shopAddress.getStreet())
                .city(shopAddress.getCity())
                .state(shopAddress.getState())
                .longitude(shopAddress.getLongitude())
                .latitude(shopAddress.getLatitude())
                .build();
    }

    public ShopAddress toUpdateShopAddress(UpdateShopAddressRequest request, ShopAddress shopAddress) {
        if (request.getName() != null) shopAddress.setName(request.getName());
        if (request.getStreet() != null) shopAddress.setStreet(request.getStreet());
        if (request.getCity() != null) shopAddress.setCity(request.getCity());
        if (request.getStreet() != null) shopAddress.setState(request.getState());
        if (request.getLongitude() != null) shopAddress.setLongitude(request.getLongitude());
        if (request.getLatitude() != null) shopAddress.setLatitude(request.getLatitude());
        return shopAddress;
    }
}
