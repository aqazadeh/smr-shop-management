package smr.shop.auth.service.mapper;

import org.springframework.stereotype.Component;
import smr.shop.auth.service.dto.request.CreateUserAddressRequest;
import smr.shop.auth.service.dto.request.UpdateUserAddressRequest;
import smr.shop.auth.service.dto.response.GetUserAddressResponse;
import smr.shop.auth.service.model.UserAddress;
import smr.shop.libs.common.helper.UserHelper;

import java.util.UUID;

@Component
public class UserAddressServiceMapper {

    public UserAddress mapToUserAddress(CreateUserAddressRequest request) {
        return UserAddress.builder()
                .id(UUID.randomUUID())
                .userId(UserHelper.getUserId())
                .state(request.getState())
                .name(request.getName())
                .street(request.getStreet())
                .build();
    }

    public GetUserAddressResponse mapToResponse(UserAddress userAddress) {
        return GetUserAddressResponse.builder()
                .city(userAddress.getCity())
                .userId(userAddress.getUserId())
                .name(userAddress.getName())
                .street(userAddress.getStreet())
                .city(userAddress.getCity())
                .state(userAddress.getState())
                .build();
    }

    public void mapForUpdate(UserAddress userAddress, UpdateUserAddressRequest request) {
        if (request.getCity() != null)
            userAddress.setCity(request.getCity());
        if (request.getName() != null)
            userAddress.setName(request.getName());
        if (request.getState() != null)
            userAddress.setState(request.getState());
        if (request.getStreet() != null)
            userAddress.setStreet(request.getStreet());

    }
}