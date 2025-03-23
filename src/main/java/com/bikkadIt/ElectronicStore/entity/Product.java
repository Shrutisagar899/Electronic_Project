package com.bikkadIt.ElectronicStore.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity // data ko store karne ke liye
@Table(name = "Products")// create a table name or table
public class Product {
    @Id
    private String productId;
    private String title;
    @Column(length = 10000)
    private String description;
    private int price;
    private int quantity;
    private int discountedPrice;
    private Date addedDate;
    private boolean live;
    private boolean inStock;
    private String productImageName;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="category")
    private Category category;

}
