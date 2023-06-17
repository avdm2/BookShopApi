package org.colloquium_m4.domain.repositories;

import org.colloquium_m4.domain.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    boolean existsByCartId(Integer cartId);
}
