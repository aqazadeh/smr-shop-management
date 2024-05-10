package smr.shop.wishlist.service.service.impl;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import smr.shop.libs.common.constant.ServiceConstants;
import smr.shop.wishlist.service.dto.response.WishlistResponse;
import smr.shop.wishlist.service.exception.WishlistException;
import smr.shop.wishlist.service.mapper.WishlistServiceMapper;
import smr.shop.wishlist.service.model.WishlistEntity;
import smr.shop.wishlist.service.repository.WishlistRepository;
import smr.shop.wishlist.service.service.WishlistService;

import java.util.List;

import static org.springframework.data.jpa.domain.AbstractPersistable_.id;

@Service
public class WishlistServiceImpl implements WishlistService {

    private WishlistServiceMapper wishlistServiceMapper;
    private WishlistRepository wishlistRepository;


    @Override
    public WishlistResponse createWishlist(WishlistEntity wishlistEntity) {
        wishlistEntity = new WishlistEntity();
        return wishlistServiceMapper.wishlistEntityToWishlistResponse(wishlistEntity);
    }

    @Override
    public void deleteWishlist(Long wishlistId) {
        WishlistEntity wishlistEntity = findById(wishlistId);
        wishlistRepository.delete(wishlistEntity);
    }

    @Override
    public void deleteProduct(Long productId) {
        wishlistRepository.deleteById(productId);
    }

    @Override
    public List<WishlistResponse> getAllWishlistProducts(Integer page) {
        Pageable pageable = PageRequest.of(page, ServiceConstants.pageSize);
        List<WishlistEntity> wishlistResponseList = wishlistRepository.findAll(pageable).stream().toList();
        return wishlistServiceMapper.wishlistEntityListToWishlistResponse(wishlistResponseList);
    }

    @Override
    public WishlistEntity findById(Long wishlistId) {
        return wishlistRepository.findById(wishlistId)
                .orElseThrow(() -> new WishlistException("Flash Deal not found with id: " + id, HttpStatus.NOT_FOUND));
    }

    @Override
    public WishlistResponse findProductById(Long productId) {
        WishlistEntity wishlistEntity = findById(productId);
        return wishlistServiceMapper.wishlistEntityToWishlistResponse(wishlistEntity);

    }
}
