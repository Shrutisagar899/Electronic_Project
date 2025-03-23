package com.bikkadIt.ElectronicStore.dtos;


import com.bikkadIt.ElectronicStore.entity.Category;
import lombok.*;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
public class ProductDto {
    private String productId;
    private String title;
    private String description;
    private int price;
    private int quantity;
    private int discountedPrice;
    private Date addedDate;
    private boolean live;
    private boolean inStock;
    private String productImageName;
    private CategoryDto category;
}
