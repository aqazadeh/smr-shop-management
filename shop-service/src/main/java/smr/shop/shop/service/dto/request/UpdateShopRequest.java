package smr.shop.shop.service.dto.request;

import lombok.Value;
import smr.shop.shop.service.model.valueobject.ShopAddress;

@Value
public class UpdateShopRequest {
    String name;
    String slug;
    String description;
    String phone;
}
