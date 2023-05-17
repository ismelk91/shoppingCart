package se.iths.shoppingcart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import se.iths.shoppingcart.business.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
