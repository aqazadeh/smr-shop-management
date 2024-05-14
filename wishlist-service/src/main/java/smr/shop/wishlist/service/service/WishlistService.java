package smr.shop.wishlist.service.service;

import smr.shop.libs.common.dto.message.ProductDeleteMessageModel;
import smr.shop.wishlist.service.dto.response.WishlistResponse;

import java.util.List;


public interface WishlistService {


    void addProductToWishlist(Long productId);


    void deleteProductInUserWishlist(Long productId);


    void deleteProductsInWishlist(ProductDeleteMessageModel productDeleteMessageModel);

    List<WishlistResponse> getAllWishlistProducts(Integer page);


}
