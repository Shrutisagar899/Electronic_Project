package com.bikkadIt.ElectronicStore.dtos;

import com.bikkadIt.ElectronicStore.entity.Cart;
import com.bikkadIt.ElectronicStore.entity.Product;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemDto {
    private int cartItemId;

    private Product product;

    private int quantity;

    private int totalPrice;

}
