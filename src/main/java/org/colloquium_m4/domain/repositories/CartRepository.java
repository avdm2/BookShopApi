package org.colloquium_m4.domain.repositories;

import org.colloquium_m4.domain.entities.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, Integer> {
}
