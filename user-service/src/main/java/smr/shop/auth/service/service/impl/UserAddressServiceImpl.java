package smr.shop.auth.service.service.impl;

import org.springframework.stereotype.Service;
import smr.shop.auth.service.dto.request.CreateUserAddressRequest;
import smr.shop.auth.service.dto.request.UpdateUserAddressRequest;
import smr.shop.auth.service.dto.response.GetUserAddressResponse;
import smr.shop.auth.service.helper.UserAddressServiceHelper;
import smr.shop.auth.service.mapper.UserAddressServiceMapper;
import smr.shop.auth.service.model.UserAddress;
import smr.shop.auth.service.repository.UserAddressRepository;
import smr.shop.auth.service.service.UserAddressService;

import java.util.UUID;
@Service
public class UserAddressServiceImpl implements UserAddressService {
    private final UserAddressRepository userAddressRepository;
    private final UserAddressServiceMapper userAddressServiceMapper;
    private final UserAddressServiceHelper userAddressServiceHelper;

    public UserAddressServiceImpl(UserAddressServiceMapper userAddressServiceMapper,
                                  UserAddressRepository userAddressRepository,
                                  UserAddressServiceHelper userAddressServiceHelper) {
        this.userAddressRepository = userAddressRepository;
        this.userAddressServiceMapper = userAddressServiceMapper;
        this.userAddressServiceHelper = userAddressServiceHelper;
    }

//    ----------------------------------- Create or Add -----------------------------------

    @Override
    public CreateUserAddressRequest createUserAddress(CreateUserAddressRequest request) {
        UserAddress userAddress = userAddressServiceMapper.mapToUserAddress(request);
        userAddressRepository.save(userAddress);
        return request;
    }

//    ----------------------------------- Update -----------------------------------

    @Override
    public void updateUserAddress(UUID userAddressId, UpdateUserAddressRequest updateUserAddressRequest) {
        UserAddress userAddress = userAddressServiceHelper.findById(userAddressId);
        userAddressServiceMapper.mapForUpdate(userAddress, updateUserAddressRequest);
        userAddressRepository.save(userAddress);
    }

//    ----------------------------------- Delete -----------------------------------

    @Override
    public void deleteUserAddressById(UUID userAddressId) {
        userAddressServiceHelper.findById(userAddressId);
        userAddressRepository.deleteById(userAddressId);
    }

//    ----------------------------------- Get -----------------------------------

    @Override
    public GetUserAddressResponse getUserAddressById(UUID userAddressId) {
        UserAddress userAddress = userAddressServiceHelper.findById(userAddressId);
        return userAddressServiceMapper.mapToResponse(userAddress);
    }

}