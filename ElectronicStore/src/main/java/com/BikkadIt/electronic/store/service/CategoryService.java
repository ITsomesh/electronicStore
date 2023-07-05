package com.BikkadIt.electronic.store.service;

import com.BikkadIt.electronic.store.dtos.CategoryDto;
import com.BikkadIt.electronic.store.dtos.PageableResponse;

public interface CategoryService {

    //create
    CategoryDto create(CategoryDto categoryDto);

    //update
    CategoryDto update(CategoryDto categoryDto,String categoryId);

    //getAll
    PageableResponse<CategoryDto>getAll(int pageSize,int pageNumber,String sortBy,String sortDir);

    //delete
    void delete(String categoryId);

    //get Single Category Details

}
