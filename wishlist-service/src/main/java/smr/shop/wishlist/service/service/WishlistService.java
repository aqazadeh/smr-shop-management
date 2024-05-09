package smr.shop.wishlist.service.service;

import smr.shop.wishlist.service.dto.response.WishlistResponse;
import smr.shop.wishlist.service.model.WishlistEntity;

import java.util.List;


public interface WishlistService {

    WishlistResponse createWishlist(WishlistEntity wishlistEntity);

    void deleteWishlist(Long wishlistId);

    void deleteProduct(Long productId);

    List<WishlistResponse> getAllWishlistProducts(Integer page);

    WishlistEntity findById(Long wishlistId);
    WishlistResponse findProductById(Long productId);

}
