package smr.shop.wishlist.service.mapper;

import org.springframework.stereotype.Component;
import smr.shop.wishlist.service.dto.response.WishlistResponse;
import smr.shop.wishlist.service.model.WishlistEntity;

import java.util.List;

@Component
public class WishlistServiceMapper {
    public WishlistResponse wishlistEntityToWishlistResponse(WishlistEntity entity){
        return WishlistResponse.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .productId(entity.getProductId())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    public List<WishlistResponse> wishlistEntityListToWishlistResponse(List<WishlistEntity> entityList){
        return entityList.stream().map(this::wishlistEntityToWishlistResponse).toList();
    }


}
