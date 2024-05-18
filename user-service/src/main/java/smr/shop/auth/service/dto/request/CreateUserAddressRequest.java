package smr.shop.auth.service.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateUserAddressRequest {
    @NotBlank
    String name;
    @NotBlank
    String street;
    @NotBlank
    String city;
    @NotBlank
    String state;
}