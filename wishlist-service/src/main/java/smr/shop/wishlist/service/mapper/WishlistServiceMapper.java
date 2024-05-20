package smr.shop.wishlist.service.mapper;

import org.springframework.stereotype.Component;
import smr.shop.libs.common.constant.ServiceConstants;
import smr.shop.libs.grpc.product.ProductGrpcResponse;
import smr.shop.wishlist.service.dto.response.WishlistProductResponse;
import smr.shop.wishlist.service.dto.response.WishlistResponse;
import smr.shop.wishlist.service.model.WishlistEntity;

import java.math.BigDecimal;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;

@Component
public class WishlistServiceMapper {
    public WishlistResponse wishlistEntityToWishlistResponse(WishlistEntity entity) {
        return WishlistResponse.builder()
                .id(entity.getId())
                .build();
    }


    public WishlistEntity productIdAndUserIdToWishlist(Long productId, UUID userId) {
        return WishlistEntity.builder()
                .userId(userId)
                .productId(productId)
                .build();
    }

    public WishlistProductResponse productGrpcResponseToWishlistProductResponse(ProductGrpcResponse productGrpcResponse) {
        return WishlistProductResponse.builder()
                .id(productGrpcResponse.getId())
                .name(productGrpcResponse.getName())
                .slug(productGrpcResponse.getSlug())
                .thumbnail(productGrpcResponse.getThumbnail())
                .price(BigDecimal.valueOf(productGrpcResponse.getPrice()))
                .discountPrice(productGrpcResponse.getDiscount().getAmount())
                .build();
    }
}
