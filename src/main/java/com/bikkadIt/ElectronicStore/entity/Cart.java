package com.bikkadIt.ElectronicStore.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "cart")
public class Cart
 {
    @Id
    private String cartId;

    private Date createdAt;
    @OneToOne
    private User user;

    //mapping cart

     @OneToMany(mappedBy = "cart",cascade = CascadeType.ALL, fetch = FetchType.LAZY)
     private List<CartItem> items= new ArrayList<CartItem>();

     @Override
     public String toString() {
         return "Cart{" +
                 "cartId='" + cartId + '\'' +
                 ", createdAt=" + createdAt +
                 ", user=" + user +
                 ", items=" + items +
                 '}';
     }
 }
