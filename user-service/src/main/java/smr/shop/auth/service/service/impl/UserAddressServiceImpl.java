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

    @Override
    public CreateUserAddressRequest create(CreateUserAddressRequest request) {
        UserAddress userAddress = userAddressServiceMapper.mapToUserAddress(request);
        userAddressRepository.save(userAddress);
        return request;
    }

    @Override
    public GetUserAddressResponse getById(UUID userAddressId) {
        UserAddress userAddress = userAddressServiceHelper.findById(userAddressId);
        return userAddressServiceMapper.mapToResponse(userAddress);
    }

    @Override
    public void deleteById(UUID userAddressId) {
        userAddressServiceHelper.findById(userAddressId);
        userAddressRepository.deleteById(userAddressId);
    }

    @Override
    public void update(UUID userAddressId, UpdateUserAddressRequest updateUserAddressRequest) {
        UserAddress userAddress = userAddressServiceHelper.findById(userAddressId);
        userAddressServiceMapper.mapForUpdate(userAddress, updateUserAddressRequest);
        userAddressRepository.save(userAddress);
    }
}