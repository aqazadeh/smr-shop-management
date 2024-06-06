package smr.shop.user.service.dto.request;

import lombok.Data;
@Data
public class UpdateUserAddressRequest {
    String name;
    String street;
    String city;
    String state;
}