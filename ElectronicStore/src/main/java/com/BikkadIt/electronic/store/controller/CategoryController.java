package com.BikkadIt.electronic.store.controller;

import com.BikkadIt.electronic.store.Constants.AppConstants;
import com.BikkadIt.electronic.store.dtos.ApiResponse;
import com.BikkadIt.electronic.store.dtos.CategoryDto;
import com.BikkadIt.electronic.store.dtos.PageableResponse;
import com.BikkadIt.electronic.store.dtos.ProductDto;
import com.BikkadIt.electronic.store.service.CategoryService;
import com.BikkadIt.electronic.store.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
@Slf4j
@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;


    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
       log.info("Request starting for service layer to create the Category");
        CategoryDto categoryDto1 = categoryService.create(categoryDto);
        log.info("Request completed for service layer to create the Category");
        return new ResponseEntity<>(categoryDto1, HttpStatus.CREATED);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto,
                                                      @PathVariable String categoryId) {
        log.info("Request starting for service layer to Update the Category {} :",categoryId);
        CategoryDto updatedCategory = categoryService.update(categoryDto, categoryId);
        log.info("Request completed for service layer to Update the Category {} :",categoryId);
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);

    }

    @DeleteMapping("/delete/{categoryId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable String categoryId) {
        log.info("Request starting for service layer to Delete the Category {} :",categoryId);
        categoryService.delete(categoryId);
        ApiResponse message = ApiResponse.builder()
                .message(AppConstants.CATEGORY_DELETE)
                .success(true)
                .status(HttpStatus.OK)
                .build();
        log.info("Request completed for service layer to Delete the Category {} :",categoryId);
        return new ResponseEntity<>(message, HttpStatus.OK);


    }
    @GetMapping("/getAllCategory")
    public ResponseEntity<PageableResponse<CategoryDto>>getAllUser(
            @RequestParam(value="pageNumber",defaultValue = "1",required = false)int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "2",required = false)int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "title",required = false)String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false)String sortDir
    ){
        log.info("Request starting for service layer to Get All  the Category");
        PageableResponse<CategoryDto> allCategory = categoryService.getAll(pageNumber, pageSize, sortBy, sortDir);
        log.info("Request completed for service layer to Get All  the Category ");
        return new ResponseEntity<>(allCategory,HttpStatus.OK);
    }

    //create product with category
    @PostMapping("/{categoryId}/product")
    public ResponseEntity<ProductDto>createProductWithCategory(
            @PathVariable ("categoryId")String categoryId,
            @RequestBody ProductDto productDto
            ){
        log.info("Request starting for service layer to post the Category {} :",categoryId);
        ProductDto proWithCategory = productService.createWithCategory(productDto, categoryId);
        log.info("Request completed for service layer to post the Category {} :",categoryId);
        return  new ResponseEntity<>(proWithCategory,HttpStatus.CREATED);

    }

    //update cat of product

    @PutMapping("/{categoryId}/product/{productId}")
    public ResponseEntity<ProductDto> updateCategoryOfProduct(
            @PathVariable String categoryId,
            @PathVariable String productId
    ){

        ProductDto productDto = productService.updateCategory(productId, categoryId);
        return new ResponseEntity<>(productDto,HttpStatus.OK);
    }

    //get all product using category

    @GetMapping("/{categoryId}/product")
    public ResponseEntity<PageableResponse<ProductDto>>getProductOfCategory(
            @PathVariable String categoryId,
            @RequestParam(value="pageNumber",defaultValue = "1",required = false)int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "2",required = false)int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "title",required = false)String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false)String sortDir)
    {

        PageableResponse<ProductDto> allOfCategory = productService
                .getAllOfCategory(categoryId,pageNumber,pageSize,sortBy,sortDir);
        return new ResponseEntity<>(allOfCategory,HttpStatus.OK);

    }

    @GetMapping("/{catId}")
    public ResponseEntity<CategoryDto>getById(@PathVariable String catId){
        CategoryDto byId = this.categoryService.getById(catId);
        return new ResponseEntity<>(byId,HttpStatus.OK);
    }
}

