package org.colloquium_m4.services;

import org.colloquium_m4.domain.entities.Order;
import org.colloquium_m4.domain.repositories.OrderRepository;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    public boolean existsByCartId(Integer cartId) {
        return orderRepository.existsByCartId(cartId);
    }
}
