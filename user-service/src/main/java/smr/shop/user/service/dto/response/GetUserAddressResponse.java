package smr.shop.user.service.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;
@Data
@Builder
public class GetUserAddressResponse {
    UUID userId;
    String name;
    String street;
    String city;
    String state;
}