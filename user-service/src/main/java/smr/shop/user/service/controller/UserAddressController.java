package smr.shop.user.service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.*;
import smr.shop.user.service.dto.request.CreateUserAddressRequest;
import smr.shop.user.service.dto.request.UpdateUserAddressRequest;
import smr.shop.user.service.dto.response.GetUserAddressResponse;
import smr.shop.user.service.service.UserAddressService;
import smr.shop.libs.common.dto.response.EmptyResponse;
import smr.shop.libs.common.helper.UserHelper;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/1.0/user/address")
public class UserAddressController {
    private final UserAddressService userAddressService;

    public UserAddressController(UserAddressService userAddressService) {
        this.userAddressService = userAddressService;
    }

//    ----------------------------------- Post -----------------------------------

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Created user address", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = CreateUserAddressRequest.class))),
            @ApiResponse(responseCode = "401", description = "Authentication required!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Permission required!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not found!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    public CreateUserAddressRequest createUserAddress(@RequestBody @Valid CreateUserAddressRequest request) {
        return userAddressService.createUserAddress(request);
    }

//    ----------------------------------- Put or Patch -----------------------------------

    @PatchMapping("/{userAddressId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Updated user address", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EmptyResponse.class))),
            @ApiResponse(responseCode = "401", description = "Authentication required!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Permission required!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not found!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<EmptyResponse> updateUserAddress(@PathVariable UUID userAddressId,
                                            @RequestBody @Valid UpdateUserAddressRequest updateUserAddressRequest) {
        userAddressService.updateUserAddress(userAddressId, updateUserAddressRequest);
        EmptyResponse response = EmptyResponse.builder()
                .message("User address updated successfully")
                .build();
        return ResponseEntity.ok(response);
    }

//    ----------------------------------- Delete -----------------------------------

    @DeleteMapping("/{userAddressId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Deleted user address", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = EmptyResponse.class))),
            @ApiResponse(responseCode = "401", description = "Authentication required!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Permission required!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not found!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<EmptyResponse> deleteUserAddressById(@PathVariable UUID userAddressId) {
        userAddressService.deleteUserAddressById(userAddressId);
        EmptyResponse response = EmptyResponse.builder()
                .message("User address deleted successfully")
                .build();
        return ResponseEntity.ok(response);
    }

//    ----------------------------------- Get -----------------------------------

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get user address", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully!", content = @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = GetUserAddressResponse.class)))),
            @ApiResponse(responseCode = "401", description = "Authentication required!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Permission required!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not found!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<List<GetUserAddressResponse>> getUserAddresses() {
        UUID userId = UserHelper.getUserId();
        return ResponseEntity.ok(userAddressService.getUserAddresses(userId));
    }

    @GetMapping("/{userAddressId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get user address", description = "")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GetUserAddressResponse.class))),
            @ApiResponse(responseCode = "401", description = "Authentication required!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "403", description = "Permission required!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not found!", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<GetUserAddressResponse> getUserAddressById(@PathVariable UUID userAddressId) {
        return ResponseEntity.ok(userAddressService.getUserAddressById(userAddressId));
    }

}