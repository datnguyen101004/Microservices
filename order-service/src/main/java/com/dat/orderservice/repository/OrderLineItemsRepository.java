package com.dat.orderservice.repository;

import com.dat.orderservice.entity.OrderLineItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderLineItemsRepository extends JpaRepository<OrderLineItems, Long> {
}
