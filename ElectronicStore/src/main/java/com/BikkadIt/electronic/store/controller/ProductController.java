package com.BikkadIt.electronic.store.controller;

import com.BikkadIt.electronic.store.dtos.*;
import com.BikkadIt.electronic.store.service.FileService;
import com.BikkadIt.electronic.store.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.io.InputStream;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;
    @Autowired
    private FileService fileService;
    @Value("${product.image.path}")
    private String imagePath;

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
            @RequestParam(value = "pageSize",defaultValue = "5",required = false)int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "title",required = false)String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false)String sortDir
    ){

        PageableResponse<ProductDto> pageResponse = productService.getAll(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(pageResponse,HttpStatus.OK);
    }
    @GetMapping("/getAllLive")
    public ResponseEntity<PageableResponse<ProductDto>>getAllLive(
            @RequestParam(value="pageNumber",defaultValue = "0",required = false)int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "5",required = false)int pageSize,
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

        PageableResponse<ProductDto> pageResponse = productService.searchByTitle(keyWord, pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(pageResponse,HttpStatus.OK);
    }
    @PostMapping("/image/{productId}")
    public ResponseEntity<ImageResponse>uploadProductImage(
            @PathVariable String productId,
            @RequestParam("productImage")MultipartFile image
            ) throws IOException {
        String fileName = fileService.uploadFile(image, imagePath);
        ProductDto productDto = productService.getById(productId);
        productDto.setProductImageName(fileName);
        ProductDto updatedProduct = productService.update(productDto, productId);
        ImageResponse productImageIsUploaded = ImageResponse.builder().imageName(updatedProduct.getProductImageName())
                .message("Product image is uploaded")
                .status(HttpStatus.CREATED)
                .success(true)
                .build();

        return new ResponseEntity<>(productImageIsUploaded,HttpStatus.CREATED);
    }
    @GetMapping("/image/{productId}")
    public void serveUserImage(@PathVariable String productId, HttpServletResponse response) throws IOException {

        ProductDto productDto = productService.getById(productId);
        InputStream resource = fileService.getResource(imagePath, productDto.getProductImageName());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());

    }
}

