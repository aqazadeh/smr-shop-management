package smr.shop.user.service.mapper;

import org.springframework.stereotype.Component;
import smr.shop.user.service.dto.request.CreateUserAddressRequest;
import smr.shop.user.service.dto.request.UpdateUserAddressRequest;
import smr.shop.user.service.dto.response.GetUserAddressResponse;
import smr.shop.user.service.model.UserAddress;

import java.util.UUID;

@Component
public class UserAddressServiceMapper {

    public UserAddress mapToUserAddress(CreateUserAddressRequest request) {
        return UserAddress.builder()
                .id(UUID.randomUUID())
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