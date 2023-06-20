package com.BikkadIt.electronic.store.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "categories")
public class Category {

    @Id
    @Column(name="id")
    private String categoryId;
    @Column(name="category_tittle",length = 60,nullable = false)
    private String tittle;
    @Column(name="category_description",length = 50,nullable = false)
    private String description;
    private String coverImage;

    @OneToMany(mappedBy = "category",cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<Product>products=new ArrayList<>();



}
