package smr.shop.wishlist.service.service;

import smr.shop.libs.common.dto.message.ProductMessageModel;
import smr.shop.wishlist.service.dto.response.WishlistResponse;
import smr.shop.wishlist.service.model.WishlistEntity;

import java.util.List;
import java.util.UUID;


public interface WishlistService {


    void addProductToWishlist(Long productId);

    void deleteProductsInWishlist(ProductMessageModel productMessageModel);

    List<WishlistResponse> getAllWishlistProducts(Integer page);


    void deleteWishlist(UUID wishlistId);

    WishlistEntity findById(UUID wishlistId);
}
