package smr.shop.user.service.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import smr.shop.libs.common.helper.UserHelper;
import smr.shop.user.service.dto.request.CreateUserAddressRequest;
import smr.shop.user.service.dto.request.UpdateUserAddressRequest;
import smr.shop.user.service.dto.response.GetUserAddressResponse;
import smr.shop.user.service.exception.UserServiceException;
import smr.shop.user.service.helper.UserAddressServiceHelper;
import smr.shop.user.service.mapper.UserAddressServiceMapper;
import smr.shop.user.service.model.UserAddress;
import smr.shop.user.service.repository.UserAddressRepository;
import smr.shop.user.service.service.UserAddressService;

import java.util.List;
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
        userAddress.setUserId(UserHelper.getUserId());
        userAddressRepository.save(userAddress);
        return request;
    }

//    ----------------------------------- Update -----------------------------------

    @Override
    public void updateUserAddress(UUID userAddressId, UpdateUserAddressRequest updateUserAddressRequest) {
        UserAddress userAddress = userAddressServiceHelper.findById(userAddressId);
        validateAddress(userAddress);
        userAddressServiceMapper.mapForUpdate(userAddress, updateUserAddressRequest);
        userAddressRepository.save(userAddress);
    }

//    ----------------------------------- Delete -----------------------------------

    @Override
    public void deleteUserAddressById(UUID userAddressId) {
        UserAddress userAddress = userAddressServiceHelper.findById(userAddressId);
        validateAddress(userAddress);
        userAddressRepository.deleteById(userAddressId);
    }

//    ----------------------------------- Get -----------------------------------

    @Override
    public GetUserAddressResponse getUserAddressById(UUID userAddressId) {
        UserAddress userAddress = userAddressServiceHelper.findById(userAddressId);
        return userAddressServiceMapper.mapToResponse(userAddress);
    }

    @Override
    public List<GetUserAddressResponse> getUserAddresses(UUID userId) {
        List<UserAddress> userAddresses = userAddressRepository.findAllByUserId(userId);
        return userAddresses.stream().map(userAddressServiceMapper::mapToResponse).toList();
    }

    private static void validateAddress(UserAddress userAddress) {
        if(!userAddress.getUserId().equals(UserHelper.getUserId())){
            throw new UserServiceException("you don't have a permission delete this with id: " + userAddress.getId(), HttpStatus.FORBIDDEN);
        }
    }
}