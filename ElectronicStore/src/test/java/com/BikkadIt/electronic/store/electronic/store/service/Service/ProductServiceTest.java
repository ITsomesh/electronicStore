package com.BikkadIt.electronic.store.electronic.store.service.Service;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.BikkadIt.electronic.store.dtos.CategoryDto;
import com.BikkadIt.electronic.store.dtos.PageableResponse;
import com.BikkadIt.electronic.store.dtos.ProductDto;
import com.BikkadIt.electronic.store.dtos.UserDto;
import com.BikkadIt.electronic.store.entities.Category;
import com.BikkadIt.electronic.store.entities.Product;
import com.BikkadIt.electronic.store.entities.User;
import com.BikkadIt.electronic.store.repository.CategoryRepo;
import com.BikkadIt.electronic.store.repository.ProductRepository;
import com.BikkadIt.electronic.store.service.CategoryService;
import com.BikkadIt.electronic.store.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.Column;
import java.util.Arrays;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class ProductServiceTest {
    @MockBean
    private ProductRepository productRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ModelMapper modelMapper;

    Product product;

    Category category;

    @BeforeEach
    public void init() {
        product = Product.builder()
                .title("IGBT")
                .description("Electronic")
                .price(12)
                .discountedPrice(10)
                .quantity(10)
                .productImageName("igbt.png")
                .build();
    }
    @Test
    public void createProductTest()
    {
        Mockito.when(productRepository.save(Mockito.any())).thenReturn(product);
        ProductDto productDto = productService.create(modelMapper.map(product, ProductDto.class));

        System.out.println(productDto.getTitle());
        System.out.println(productDto.getDescription());

        Assertions.assertNotNull(productDto);
        Assertions.assertEquals("IGBT", productDto.getTitle());
        Assertions.assertEquals("Electronic", productDto.getDescription());

    }
    @Test
    public void updateProductTest() {
        String productId = " ";
        ProductDto productDto = ProductDto.builder()
                .title("Iphone")
                .description("Electronic")
                .price(10)
                .discountedPrice(9)
                .quantity(10)
                .productImageName("iphone.png")
                .build();
        Mockito.when(productRepository.findById(Mockito.anyString())).thenReturn(Optional.of(product));
        Mockito.when(productRepository.save(Mockito.any())).thenReturn(product);
        ProductDto productDto1 = productService.update(productDto, productId);
        //UserDto map = modelMapper.map(user, UserDto.class);
        ProductDto map = modelMapper.map(product, ProductDto.class);
        System.out.println(productDto.getTitle());
        System.out.println(productDto.getPrice());
        System.out.println(productDto.getDiscountedPrice());
        Assertions.assertEquals(product.getTitle(), productDto1.getTitle(), "Not Matched");
    }


    @Test
    public void deleteProductTest() {
        String productid = "asdf";
        Mockito.when(productRepository.findById("asdf")).thenReturn(Optional.of(product));
        productService.delete(productid);
        Mockito.verify(productRepository, Mockito.times(1)).delete(product);
        System.out.println("Product Delete Test Passed");
    }

    @Test
    public void getProductByIdTest() {
        String productid = "asdf";
        Mockito.when(productRepository.findById(productid)).thenReturn(Optional.of(product));
        ProductDto productDto = productService.getById(productid);
        Assertions.assertNotNull(productDto);
        Assertions.assertEquals(product.getTitle(), productDto.getTitle(), "Name Not Matched");
    }

    @Test
    public void getAllProductTest() {
        Product product1 = Product.builder()
                .title("Iphone")
                .description("Electronic")
                .price(10)
                .discountedPrice(9)
                .quantity(10)
                .productImageName("iphone.png")
                .build();

        Product product2 = Product.builder()
                .title("LG")
                .description("Electronic")
                .price(10)
                .discountedPrice(9)
                .quantity(10)
                .productImageName("iphone.png")
                .build();
        List<Product> productList = Arrays.asList(product, product1, product2);
        Page<Product> page = new PageImpl<>(productList);
        Mockito.when(productRepository.findAll((Pageable) Mockito.any())).thenReturn(page);
        PageableResponse<ProductDto> allProduct = productService.getAll(1, 2, "name", "ascending");
        //Assertions.assertEquals(3,allUser.getContent().size());
        Assertions.assertEquals(3, allProduct.getContent().size());
    }




}

