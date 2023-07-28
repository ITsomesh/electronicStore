package com.BikkadIt.electronic.store.service;

import com.BikkadIt.electronic.store.dtos.AddItemToCartRequest;
import com.BikkadIt.electronic.store.dtos.CartDto;

public interface CartService {

    CartDto addItemToCart(String userId, AddItemToCartRequest request);
    void removeItemFromCart(String userId,int cartItem);
    void clearCart(String userId);

    CartDto getCartByUser(String userId);

}
