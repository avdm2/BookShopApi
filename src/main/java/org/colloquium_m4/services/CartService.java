package org.colloquium_m4.services;

import org.colloquium_m4.domain.entities.Cart;
import org.colloquium_m4.domain.repositories.CartRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class CartService {

    private final CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public Cart saveCart(Cart cart) {
        return cartRepository.save(cart);
    }

    public Optional<Cart> findById(Integer id) {
        return cartRepository.findById(id);
    }
}
