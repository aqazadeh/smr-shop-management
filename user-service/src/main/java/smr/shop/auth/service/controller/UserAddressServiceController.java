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

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)

    public CreateUserAddressRequest create(@RequestBody @Valid CreateUserAddressRequest request) {
        return userAddressService.create(request);
    }

    @GetMapping("/{userAddressId}")
    public GetUserAddressResponse getById(@PathVariable UUID userAddressId) {
        return userAddressService.getById(userAddressId);
    }

    @DeleteMapping("/{userAddressId}")
    public void deleteById(@PathVariable UUID userAddressId) {
        userAddressService.deleteById(userAddressId);
    }

    @PatchMapping("/{userAddressId}")
    public void update(@PathVariable UUID userAddressId,
                       @RequestBody @Valid UpdateUserAddressRequest updateUserAddressRequest) {
        userAddressService.update(userAddressId, updateUserAddressRequest);
    }

}