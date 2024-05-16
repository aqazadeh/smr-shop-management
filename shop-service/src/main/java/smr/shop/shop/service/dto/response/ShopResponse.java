package smr.shop.shop.service.dto.response;

import lombok.Builder;
import lombok.Value;
import smr.shop.shop.service.model.valueobject.ShopAddress;
import smr.shop.shop.service.model.valueobject.ShopStatus;

import java.util.UUID;

@Value
@Builder
public class ShopResponse {
    Long id;
    UUID userId;
    String name;
    String slug;
    String description;
    String logo;
    String phone;
    ShopAddress address;
    Double rating;
    Integer reviewsCount;
    Integer salesCount;
    Integer viewCount;
    ShopStatus status;
}
