package org.colloquium_m4.controllers;

import org.colloquium_m4.domain.entities.Cart;
import org.colloquium_m4.domain.entities.Order;
import org.colloquium_m4.domain.entities.Response;
import org.colloquium_m4.services.CartService;
import org.colloquium_m4.services.OrderService;
import org.hibernate.Hibernate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    public OrderService orderService;
    public CartService cartService;

    public OrderController(OrderService orderService, CartService cartService) {
        this.orderService = orderService;
        this.cartService = cartService;
    }

    @PostMapping("/{id}")
    public ResponseEntity<Response> createOrder(@PathVariable Integer id) {
        Response response = new Response();
        Optional<Cart> optionalCart = cartService.findById(id);
        if (optionalCart.isEmpty()) {
            return new ResponseEntity<>(response.setString("Неверное ID корзины!"), HttpStatus.BAD_REQUEST);
        }
        if (orderService.existsByCartId(id)) {
            return new ResponseEntity<>(response.setString("Заказ для этой корзины уже существует!"), HttpStatus.BAD_REQUEST);
        }
        Cart cart = optionalCart.get();
        Hibernate.initialize(cart);
        Order order = new Order().setCart(cart);
        orderService.saveOrder(order);
        return new ResponseEntity<>(response.setString("Заказ оформлен!").setOrder(order), HttpStatus.OK);
    }
}
