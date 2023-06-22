package com.BikkadIt.electronic.store.controller;

import com.BikkadIt.electronic.store.Constants.AppConstants;
import com.BikkadIt.electronic.store.dtos.ApiResponse;
import com.BikkadIt.electronic.store.dtos.CategoryDto;
import com.BikkadIt.electronic.store.dtos.PageableResponse;
import com.BikkadIt.electronic.store.dtos.ProductDto;
import com.BikkadIt.electronic.store.service.CategoryService;
import com.BikkadIt.electronic.store.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;

    private Logger logger = LoggerFactory.getLogger(userController.class);


    /**
     *
     * @param categoryDto
     * @return
     */
    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        CategoryDto categoryDto1 = categoryService.create(categoryDto);
        return new ResponseEntity<>(categoryDto1, HttpStatus.CREATED);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(@RequestBody CategoryDto categoryDto,
                                                      @PathVariable String categoryId) {
        CategoryDto updatedCategory = categoryService.update(categoryDto, categoryId);
        return new ResponseEntity<>(updatedCategory, HttpStatus.OK);

    }

    @DeleteMapping("/delete/{categoryId}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable String categoryId) {

        categoryService.delete(categoryId);
        ApiResponse message = ApiResponse.builder()
                .message(AppConstants.CATEGORY_DELETE)
                .success(true)
                .status(HttpStatus.OK)
                .build();
        return new ResponseEntity<>(message, HttpStatus.OK);


    }
    @GetMapping("/getAllCategory")
    public ResponseEntity<PageableResponse<CategoryDto>>getAllUser(
            @RequestParam(value="pageNumber",defaultValue = "1",required = false)int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "2",required = false)int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "tittle",required = false)String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false)String sortDir
    ){
        PageableResponse<CategoryDto> allCategory = categoryService.getAll(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(allCategory,HttpStatus.OK);
    }

    //create product with category
    @PostMapping("/{categoryId}/product")
    public ResponseEntity<ProductDto>createProductWithCategory(
            @PathVariable ("categoryId")String categoryId,
            @RequestBody ProductDto productDto
            ){
        ProductDto proWithCategory = productService.createWithCategory(productDto, categoryId);
        return  new ResponseEntity<>(proWithCategory,HttpStatus.CREATED);

    }

}

