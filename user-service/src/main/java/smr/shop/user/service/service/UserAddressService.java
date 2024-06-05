package smr.shop.user.service.service;

import smr.shop.user.service.dto.request.CreateUserAddressRequest;
import smr.shop.user.service.dto.request.UpdateUserAddressRequest;
import smr.shop.user.service.dto.response.GetUserAddressResponse;

import java.util.List;
import java.util.UUID;

public interface UserAddressService {

    CreateUserAddressRequest createUserAddress(CreateUserAddressRequest request);

    GetUserAddressResponse getUserAddressById(UUID userAddressId);

    void deleteUserAddressById(UUID userAddressId);

    void updateUserAddress(UUID userAddressId, UpdateUserAddressRequest updateUserAddressRequest);

    List<GetUserAddressResponse> getUserAddresses(UUID userId);
}
