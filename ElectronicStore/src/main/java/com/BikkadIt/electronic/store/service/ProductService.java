package com.BikkadIt.electronic.store.service;

import com.BikkadIt.electronic.store.dtos.PageableResponse;
import com.BikkadIt.electronic.store.dtos.ProductDto;
import com.BikkadIt.electronic.store.dtos.UserDto;
import com.BikkadIt.electronic.store.entities.Product;

import java.util.List;

public interface ProductService {

    ProductDto create (ProductDto productDto);
    ProductDto update(ProductDto productDto,String productId);
    void delete (String productId);
    ProductDto getById(String productId);
    PageableResponse<ProductDto> getAll(int pageNumber,int pageSize,String sortBy,String SortDir);
    PageableResponse <ProductDto>getAllLive(int pageNumber,int pageSize,String sortBy,String SortDir);
    PageableResponse <ProductDto>searchByTitle(String subTitle,int pageNumber,int pageSize,String sortBy,String SortDir);
    ProductDto createWithCategory(ProductDto productDto,String categoryId);
    ProductDto updateCategory(String productId,String categoryId);
    PageableResponse<ProductDto>getAllOfCategory(String categoryId,int pageNumber,int pageSize,String sortBy,String sortDir);


}



