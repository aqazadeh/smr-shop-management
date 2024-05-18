package smr.shop.auth.service.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import smr.shop.auth.service.dto.request.CreateUserAddressRequest;
import smr.shop.auth.service.dto.request.UpdateUserAddressRequest;
import smr.shop.auth.service.dto.response.GetUserAddressResponse;
import smr.shop.auth.service.service.UserAddressService;

import java.util.UUID;

@RestController
@RequestMapping("/api/1.0/user/address")
public class UserAddressServiceController {
    private final UserAddressService userAddressService;

    public UserAddressServiceController(UserAddressService userAddressService) {
        this.userAddressService = userAddressService;
    }

//    ----------------------------------- Post -----------------------------------

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateUserAddressRequest createUserAddress(@RequestBody @Valid CreateUserAddressRequest request) {
        return userAddressService.createUserAddress(request);
    }

//    ----------------------------------- Put or Patch -----------------------------------

    @PatchMapping("/{userAddressId}")
    public void updateUserAddress(@PathVariable UUID userAddressId,
                       @RequestBody @Valid UpdateUserAddressRequest updateUserAddressRequest) {
        userAddressService.updateUserAddress(userAddressId, updateUserAddressRequest);
    }

//    ----------------------------------- Delete -----------------------------------

    @DeleteMapping("/{userAddressId}")
    public void deleteUserAddressById(@PathVariable UUID userAddressId) {
        userAddressService.deleteUserAddressById(userAddressId);
    }

//    ----------------------------------- Get -----------------------------------

    @GetMapping("/{userAddressId}")
    public GetUserAddressResponse getUserAddressById(@PathVariable UUID userAddressId) {
        return userAddressService.getUserAddressById(userAddressId);
    }

}