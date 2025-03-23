package com.bikkadIt.ElectronicStore.service.impl;

import com.bikkadIt.ElectronicStore.dtos.AddItemToCartRequest;
import com.bikkadIt.ElectronicStore.dtos.CartDto;
import com.bikkadIt.ElectronicStore.entity.Cart;
import com.bikkadIt.ElectronicStore.entity.CartItem;
import com.bikkadIt.ElectronicStore.entity.Product;
import com.bikkadIt.ElectronicStore.entity.User;
import com.bikkadIt.ElectronicStore.exception.BadApiRequestException;
import com.bikkadIt.ElectronicStore.exception.ResourceNotFoundException;
import com.bikkadIt.ElectronicStore.repository.CartItemRepository;
import com.bikkadIt.ElectronicStore.repository.CartRepository;
import com.bikkadIt.ElectronicStore.repository.ProductRepository;
import com.bikkadIt.ElectronicStore.repository.UserRepository;
import com.bikkadIt.ElectronicStore.service.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private CartItemRepository cartItemRepository;


//    @Autowired
//    private  CartItem cartItem;  // delete if not required

    @Override
    public CartDto addItemToCart(String userId, AddItemToCartRequest request) {
        int quantity = request.getQuantity();
        String productId = request.getProductId();

        if (quantity <= 0) {
            throw new BadApiRequestException("Quantity must be greater than zero.");
        }

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product with ID " + productId + " not found."));

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User with ID " + userId + " not found."));

        Cart cart = cartRepository.findByUser(user).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setCartId(UUID.randomUUID().toString());
            newCart.setCreatedAt(new Date());
            newCart.setUser(user);

            CartItem item = CartItem.builder().product(product)
                    .quantity(quantity)
                    .build();
            ArrayList<CartItem> cartItems = new ArrayList<>();
            cartItems.add(item);
            newCart.setItems(cartItems);

            return cartRepository.save(newCart); // Save new cart immediately
        });

        boolean updated = false;

        for (CartItem item : cart.getItems()) {
            if (item.getProduct().getProductId().equals(productId)) {
                item.setQuantity(quantity);
                item.setTotalPrice(quantity * product.getDiscountedPrice());
                updated = true;
                break;
            }
        }

        if (!updated) {
            CartItem cartItem = CartItem.builder()
                    .quantity(quantity)
                    .totalPrice(quantity * product.getDiscountedPrice())
                    .cart(cart)
                    .product(product)
                    .build();
            cart.getItems().add(cartItem);
        }

        Cart updatedCart = cartRepository.save(cart);
        return mapper.map(updatedCart, CartDto.class);
    }


    @Override
    public void removeItemFromCart(String userId, int cartItem) {
        // conditions
        CartItem cartItem1 = cartItemRepository.findById(cartItem)
                .orElseThrow(() -> new ResourceNotFoundException("cart item not found in "));
        cartItemRepository.delete(cartItem1);
    }

    @Override
    public void clearCart(String userId) {
        //fetch the user from db
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found in database"));
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("cart of given not found"));
        cart.getItems().clear();
        cartRepository.save(cart);
    }

    @Override
    public CartDto getCartByUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found in database"));
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("cart of given not found"));

        return mapper.map(cart, CartDto.class);
    }

    @Override
    public List<CartDto> getCarts() {
        List<Cart> carts = cartRepository.findAll();
        System.out.println("carts before mapp"+carts);
        List<CartDto> cartDtos=new ArrayList<>();
        if(!carts.isEmpty()) {
            for (Cart cart : carts) {
                CartDto map = mapper.map(cart, CartDto.class);
                cartDtos.add(map);
            }
            return  cartDtos;
        }
        else {
            return cartDtos;
        }


    }


}

