package smr.shop.auth.service.service;

import smr.shop.auth.service.dto.request.CreateUserAddressRequest;
import smr.shop.auth.service.dto.request.UpdateUserAddressRequest;
import smr.shop.auth.service.dto.response.GetUserAddressResponse;

import java.util.UUID;

public interface UserAddressService {

    CreateUserAddressRequest createUserAddress(CreateUserAddressRequest request);
    GetUserAddressResponse getUserAddressById(UUID userAddressId);
    void deleteUserAddressById(UUID userAddressId);
    void updateUserAddress(UUID userAddressId, UpdateUserAddressRequest updateUserAddressRequest);

}
