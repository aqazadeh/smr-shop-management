package smr.shop.cart.service.service.impl;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import smr.shop.cart.service.dto.response.CartItemResponse;
import smr.shop.cart.service.dto.response.CartResponse;
import smr.shop.cart.service.exception.CartException;
import smr.shop.cart.service.exception.CartItemException;
import smr.shop.cart.service.mapper.CartServiceMapper;
import smr.shop.cart.service.model.CartEntity;
import smr.shop.cart.service.model.CartItemEntity;
import smr.shop.cart.service.repository.CartItemRepository;
import smr.shop.cart.service.repository.CartRepository;
import smr.shop.cart.service.service.CartService;

import java.util.List;
import java.util.UUID;

/**
 * Author: Ali Gadashov
 * Version: v1.0
 * Date: 5/10/2024
 * Time: 1:28 PM
 */


@Service
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final CartServiceMapper cartServiceMapper;

    public CartServiceImpl(CartRepository cartRepository, CartItemRepository cartItemRepository, CartServiceMapper cartServiceMapper) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.cartServiceMapper = cartServiceMapper;
    }

    @Override
    public void deleteCart(Long cartId) {
        CartEntity cartEntity = findById(cartId);
        cartRepository.delete(cartEntity);
        // TODO if need send kafka event
    }

    @Override
    public List<CartResponse> addCoupon(String couponCode) {
        return null;
    }

    @Override
    public List<CartResponse> removeCoupon(String couponCode) {
        return null;
    }

    @Override
    public void clearCart(Long userId) {

    }

    @Override
    public void deleteCartItem(Long productId) {
        cartItemRepository.deleteByProductId(productId);
        // TODO if need send kafka event
    }

    @Override
    public List<CartItemResponse> getAllCartItems() {
        return null;
    }


    @Override
    public void increase(Long productId) {

    }

    @Override
    public void decrease(Long productId) {

    }

    @Override
    public CartItemEntity findById(UUID cartItemId) {
        return cartItemRepository.findById(cartItemId).orElseThrow(() -> new CartItemException("Cart Item Not found With id : " + cartItemId, HttpStatus.NOT_FOUND));

    }

    @Override
    public CartEntity findById(Long cartId) {
        return cartRepository.findById(cartId).orElseThrow(() -> new CartException("Cart Not found With id : " + cartId, HttpStatus.NOT_FOUND));
    }
}
