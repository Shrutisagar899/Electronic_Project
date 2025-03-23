package com.bikkadIt.ElectronicStore.repository;

import com.bikkadIt.ElectronicStore.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepository extends JpaRepository<CartItem,Integer> {
}
