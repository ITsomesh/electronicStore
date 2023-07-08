package com.BikkadIt.electronic.store.electronic.store.service.Service;

import com.BikkadIt.electronic.store.dtos.ProductDto;
import com.BikkadIt.electronic.store.entities.Product;
import com.BikkadIt.electronic.store.repository.ProductRepository;
import com.BikkadIt.electronic.store.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
@SpringBootTest
public class ProductServiceTest {
    @MockBean
    private ProductRepository productRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private ModelMapper modelMapper;

    Product product;

    @BeforeEach
    public void init(){
        product=Product.builder()
                .title("IGBT")
                .description("Electronic")
                .price(12)
                .discountedPrice(10)
                .quantity(10)
                .productImageName("igbt.png")
                .build();
    }
    @Test
    public void createProductTest(){
        Mockito.when(productRepository.save(Mockito.any())).thenReturn(product);
        ProductDto productDto = productService.create(modelMapper.map(product, ProductDto.class));

        System.out.println(productDto.getTitle());
        System.out.println(productDto.getDescription());

        Assertions.assertNotNull(productDto);
        Assertions.assertEquals("IGBT",productDto.getTitle());
        Assertions.assertEquals("Electronic",productDto.getDescription());


    }


}



