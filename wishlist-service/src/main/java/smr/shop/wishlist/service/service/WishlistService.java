package smr.shop.wishlist.service.service;

import smr.shop.wishlist.service.dto.response.WishlistResponse;

import java.util.List;


public interface WishlistService {


    void addProductToWishlist(Long productId);


    void deleteProductInUserWishlist(Long productId);


    void deleteProductsInWishlist(Long productId);

    List<WishlistResponse> getAllWishlistProducts(Integer page);


}
