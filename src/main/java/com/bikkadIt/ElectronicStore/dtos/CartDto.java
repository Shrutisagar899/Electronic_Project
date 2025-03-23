package com.bikkadIt.ElectronicStore.dtos;

import com.bikkadIt.ElectronicStore.entity.CartItem;
import com.bikkadIt.ElectronicStore.entity.User;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartDto {
    private String cartId;
    private Date createdAt;
    private User user;
    private List<CartItem> items = new ArrayList<>();

}
