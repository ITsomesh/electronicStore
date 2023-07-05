package com.BikkadIt.electronic.store.entities;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="product")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder


public class Product {
    @Id
    private String productId;
    private String title;
    @Column
    private String description;
    private int price;
    private int discountedPrice;
    private int quantity;
    private Date addedDate;
    private boolean live;
    private boolean stock;
    private String productImageName;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="category_Id")
    private Category category;

}
