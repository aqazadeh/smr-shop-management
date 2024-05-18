package smr.shop.auth.service.dto.request;

import lombok.Data;
@Data
public class UpdateUserAddressRequest {
    String name;
    String street;
    String city;
    String state;
}