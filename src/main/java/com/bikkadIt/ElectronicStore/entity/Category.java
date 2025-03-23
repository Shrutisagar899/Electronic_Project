package com.bikkadIt.ElectronicStore.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "catagory")
public class Category {
    @Id
    @Column(name = "id")
    private String catagoryId;
    @Column(name = "catagory_title", length = 60, nullable = false)
    private String title;
    @Column(name = "catagory_desc", length = 500)
    private String description;
    private String coverImage;
    //other attributes if you
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Product> products = new ArrayList<>();
}
