package com.BikkadIt.electronic.store.controller;

import com.BikkadIt.electronic.store.dtos.ApiResponse;
import com.BikkadIt.electronic.store.dtos.PageableResponse;
import com.BikkadIt.electronic.store.dtos.ProductDto;
import com.BikkadIt.electronic.store.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProjectController {
    @Autowired
    private ProductService productService;

    @PostMapping
    public ResponseEntity<ProductDto>createProduct(@RequestBody ProductDto productDto){
        ProductDto productCreated = productService.create(productDto);
        return new ResponseEntity<>(productCreated, HttpStatus.CREATED);
    }

    @PutMapping("/{Id}")
    public ResponseEntity<ProductDto>updateProduct(@PathVariable String Id,@RequestBody ProductDto productDto){
        ProductDto update = productService.update(productDto, Id);
        return new ResponseEntity<>(update,HttpStatus.OK);
    }

    @DeleteMapping("/{Id}")
    public ResponseEntity<ApiResponse>delete(@PathVariable String Id){
        productService.delete(Id);
        ApiResponse build = ApiResponse.builder().message("Product is deleted")
                .status(HttpStatus.OK)
                .success(true)
                .build();
        return new ResponseEntity<>(build,HttpStatus.OK);
    }

    @GetMapping("/{Id}")
    public ResponseEntity<ProductDto>getProduct(@PathVariable String Id){
        ProductDto byId = productService.getById(Id);
        return  new ResponseEntity<>(byId,HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<PageableResponse<ProductDto>>getAll(
            @RequestParam(value="pageNumber",defaultValue = "0",required = false)int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "1",required = false)int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "title",required = false)String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false)String sortDir
    ){

        PageableResponse<ProductDto> pageResponse = productService.getAll(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(pageResponse,HttpStatus.OK);
    }
    @GetMapping("/getAllLive")
    public ResponseEntity<PageableResponse<ProductDto>>getAllLive(
            @RequestParam(value="pageNumber",defaultValue = "0",required = false)int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "1",required = false)int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "title",required = false)String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false)String sortDir
    ){

        PageableResponse<ProductDto> pageResponse = productService.getAllLive(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(pageResponse,HttpStatus.OK);
    }

    @GetMapping("/search/{keyWord}")
    public ResponseEntity<PageableResponse<ProductDto>>searchAll(
            @PathVariable String keyWord,
            @RequestParam(value="pageNumber",defaultValue = "0",required = false)int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "1",required = false)int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "title",required = false)String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false)String sortDir
    ){

        PageableResponse<ProductDto> pageResponse = productService.searchByTitle(keyWord,pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(pageResponse,HttpStatus.OK);
    }
}

