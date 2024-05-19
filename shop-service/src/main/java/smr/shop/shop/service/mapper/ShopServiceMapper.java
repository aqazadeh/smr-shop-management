package smr.shop.shop.service.mapper;

import org.springframework.stereotype.Component;
import smr.shop.libs.common.dto.message.ShopMessageModel;
import smr.shop.libs.common.dto.message.UploadMessageModel;
import smr.shop.libs.grpc.product.shop.ShopGrpcResponse;
import smr.shop.shop.service.dto.request.CreateShopAddressRequest;
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
                .address(createShopAddressToShopAddress(request.getAddress()))
                .description(request.getDescription())
                .build();
    }

    private ShopAddress createShopAddressToShopAddress(CreateShopAddressRequest request) {
        ShopAddress shopAddress = new ShopAddress();
        shopAddress.setName(request.getName());
        shopAddress.setStreet(request.getStreet());
        shopAddress.setCity(request.getCity());
        shopAddress.setState(request.getState());
        return shopAddress;
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
                .status(shopEntity.getStatus())
                .build();
    }

    public ShopAddressResponse shopAddressToShopAddressResponse(ShopAddress shopAddress) {
        return ShopAddressResponse.builder()
                .name(shopAddress.getName())
                .street(shopAddress.getStreet())
                .city(shopAddress.getCity())
                .state(shopAddress.getState())
                .build();
    }


    public ShopAddress updateShopAddressRequestToShopAddress(UpdateShopAddressRequest request) {
        ShopAddress shopAddress = new ShopAddress();
        shopAddress.setName(request.getName());
        shopAddress.setStreet(request.getStreet());
        shopAddress.setCity(request.getCity());
        shopAddress.setState(request.getState());
        return shopAddress;
    }

    public ShopMessageModel shopEntityToShopMessageModel(ShopEntity shopEntity) {
        return ShopMessageModel.builder()
                .id(shopEntity.getId())
                .build();
    }

    public ShopGrpcResponse shopEntityToShopGrpcResponse(ShopEntity shopEntity) {
        return ShopGrpcResponse.newBuilder()
                .setId(shopEntity.getId())
                .setName(shopEntity.getShopName())
                .setUserId(shopEntity.getUserId().toString())
                .setLogo(shopEntity.getLogo())
                .setSlug(shopEntity.getSlug())
                .build();
    }

    public UploadMessageModel shopEntityToUploadMessageModel(String oldImage) {
        return UploadMessageModel.builder()
                .imageUrl(oldImage)
                .build();
    }
}
