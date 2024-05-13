package smr.shop.shop.service.dto.response;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ShopAddressResponse {
    String name;
    String street;
    String city;
    String state;
    Float longitude;
    Float latitude;
}
