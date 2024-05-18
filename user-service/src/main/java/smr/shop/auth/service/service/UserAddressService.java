package smr.shop.auth.service.service;

import smr.shop.auth.service.dto.request.CreateUserAddressRequest;
import smr.shop.auth.service.dto.request.UpdateUserAddressRequest;
import smr.shop.auth.service.dto.response.GetUserAddressResponse;

import java.util.UUID;

public interface UserAddressService {

    CreateUserAddressRequest create(CreateUserAddressRequest request);
    GetUserAddressResponse getById(UUID userAddressId);
    void deleteById(UUID userAddressId);
    void update(UUID userAddressId, UpdateUserAddressRequest updateUserAddressRequest);

}
