package com.BikkadIt.electronic.store.dtos;

import com.BikkadIt.electronic.store.entities.Product;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemDto {

    private int cartItemId;
    private ProductDto product;
    private int quantity;
    private int totalPrice;

}
