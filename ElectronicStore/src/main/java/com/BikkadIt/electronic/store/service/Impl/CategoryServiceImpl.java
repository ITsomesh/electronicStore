package com.BikkadIt.electronic.store.service.Impl;

import com.BikkadIt.electronic.store.Constants.AppConstants;
import com.BikkadIt.electronic.store.dtos.CategoryDto;
import com.BikkadIt.electronic.store.dtos.PageableResponse;
import com.BikkadIt.electronic.store.entities.Category;
import com.BikkadIt.electronic.store.exception.ResourceNotFoundException;
import com.BikkadIt.electronic.store.helper.Helper;
import com.BikkadIt.electronic.store.repository.CategoryRepo;
import com.BikkadIt.electronic.store.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepo categoryRepo;

    @Autowired
    private ModelMapper mapper;


    @Override
    public CategoryDto create(CategoryDto categoryDto) {
        String categoryId = UUID.randomUUID().toString();
        categoryDto.setCategoryId(categoryId);
        Category category = mapper.map(categoryDto, Category.class);
        Category saveCategory = categoryRepo.save(category);
        return mapper.map(saveCategory, CategoryDto.class);
    }

    @Override
    public CategoryDto update(CategoryDto categoryDto, String categoryId) {
        //get category
        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(AppConstants.CATEGORY_NOT_FOUND));

        //update category
        category.setTittle(categoryDto.getTittle());
        category.setDescription(categoryDto.getDescription());
        category.setCoverImage(categoryDto.getCoverImage());
        Category updatecategory = categoryRepo.save(category);
        return mapper.map(updatecategory,CategoryDto.class);
    }

    @Override
    public PageableResponse<CategoryDto> getAll(int pageSize,int pageNumber,String sortBy,String sortDir) {

        Sort sort = (sortDir.equalsIgnoreCase("desc"))
                ? (Sort.by(sortBy).descending())
                : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize,sort);
        Page<Category> page = categoryRepo.findAll(pageable);
        PageableResponse<CategoryDto> pageableResponse = Helper.getPageableResponse(page, CategoryDto.class);
        return pageableResponse;
    }

    @Override
    public void delete(String categoryId) {

        Category deleteCategory = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(AppConstants.CATEGORY_DELETE));
        categoryRepo.delete(deleteCategory);


    }
}
