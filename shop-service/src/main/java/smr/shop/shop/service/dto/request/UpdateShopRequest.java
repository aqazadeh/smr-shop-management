package smr.shop.shop.service.dto.request;

import lombok.Value;

@Value
public class UpdateShopRequest {
    String name;
    String slug;
    String description;
    String phone;
}
