package smr.shop.user.service.helper;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import smr.shop.user.service.exception.UserServiceException;
import smr.shop.user.service.model.UserAddress;
import smr.shop.user.service.repository.UserAddressRepository;

import java.util.UUID;

@Component
public class UserAddressServiceHelper {
    private final UserAddressRepository userAddressRepository;

    public UserAddressServiceHelper(UserAddressRepository userAddressRepository) {
        this.userAddressRepository = userAddressRepository;
    }

    public UserAddress findById(UUID userAddressId) {
        return userAddressRepository.findById(userAddressId).orElseThrow(()
                -> new UserServiceException("User address given by ID not found", HttpStatus.NOT_FOUND));

    }
}