package com.bikkadIt.ElectronicStore.service;

import com.bikkadIt.ElectronicStore.dtos.AddItemToCartRequest;
import com.bikkadIt.ElectronicStore.dtos.CartDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CartService {
    //add items to cart
    //case1: cart for user is not available:- we will create the cart and cartItem
    //case2: cart vailable add the items to cart

    CartDto addItemToCart(String userId, AddItemToCartRequest request);

    //remove item from cart:
    void removeItemFromCart(String userId, int cartItem);

    //remove all items form cart
    void clearCart(String userId);

    CartDto getCartByUser(String userId);

    List<CartDto> getCarts();


}
