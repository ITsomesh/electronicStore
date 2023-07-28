package com.BikkadIt.electronic.store.service.Impl;

import com.BikkadIt.electronic.store.dtos.AddItemToCartRequest;
import com.BikkadIt.electronic.store.dtos.CartDto;
import com.BikkadIt.electronic.store.entities.Cart;
import com.BikkadIt.electronic.store.entities.CartItem;
import com.BikkadIt.electronic.store.entities.Product;
import com.BikkadIt.electronic.store.entities.User;
import com.BikkadIt.electronic.store.exception.BadApiRequest;
import com.BikkadIt.electronic.store.exception.ResourceNotFoundException;
import com.BikkadIt.electronic.store.repository.CartItemRepository;
import com.BikkadIt.electronic.store.repository.CartRepository;
import com.BikkadIt.electronic.store.repository.ProductRepository;
import com.BikkadIt.electronic.store.repository.UserRepo;
import com.BikkadIt.electronic.store.service.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private CartItemRepository cartItemRepository;

    @Override
    public CartDto addItemToCart(String userId, AddItemToCartRequest request) {

        int quantity = request.getQuantity();
        String productId = request.getProductId();

        if(quantity<=0){
            throw new BadApiRequest("Requested quantity is not VALID");
        }
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product Not Found..."));

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found"));


        Cart cart = null;
        try {
            cart = cartRepository.findByUser(user).get();
        } catch (NoSuchElementException e) {
            cart = new Cart();
            cart.setCartId(UUID.randomUUID().toString());
            cart.setCreatedAt(new Date());
        }

        AtomicReference<Boolean>updated=new AtomicReference<>(false);
        List<CartItem> items=cart.getItems();
        List<CartItem> updatedItem= items.stream().map(item-> {
            if(item.getProduct().getProductId().equals(productId)) {
                item.setQuantity(quantity);
                item.setTotalPrice(quantity*product.getPrice());
                updated.set(true);

            }
            return item;
        }).collect(Collectors.toList());
        if(!updated.get()) {
            CartItem cartItem = CartItem.builder()
                    .quantity(quantity)
                    .totalPrice(quantity * product.getPrice())
                    .cart(cart)
                    .product(product)
                    .build();
            cart.getItems().add(cartItem);
        }
        cart.setUser(user);
        Cart updatedCart = cartRepository.save(cart);
        return mapper.map(updatedCart,CartDto.class);


    }
    @Override
    public void removeItemFromCart(String userId, int cartItem) {

        CartItem cartItem1 = cartItemRepository.findById(cartItem)
                .orElseThrow(() -> new ResourceNotFoundException("cart item not found"));
        cartItemRepository.delete(cartItem1);
    }

    @Override
    public void clearCart(String userId) {

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Cart of Given User Not Found"));
        cart.getItems().clear();
        cartRepository.save(cart);

    }

    @Override
    public CartDto getCartByUser(String userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
        Cart cart = cartRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Cart of Given User Not Found"));

        return mapper.map(cart,CartDto.class);
    }
}
