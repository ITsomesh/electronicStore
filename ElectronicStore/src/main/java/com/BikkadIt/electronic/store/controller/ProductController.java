package com.BikkadIt.electronic.store.controller;

import com.BikkadIt.electronic.store.Constants.AppConstants;
import com.BikkadIt.electronic.store.dtos.*;
import com.BikkadIt.electronic.store.service.FileService;
import com.BikkadIt.electronic.store.service.ProductService;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
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
        log.info("Request starting for service layer to create the Product");
        ProductDto productCreated = productService.create(productDto);
        log.info("Request completed for service layer to create the Product");
        return new ResponseEntity<>(productCreated, HttpStatus.CREATED);
    }

    @PutMapping("/{Id}")
    public ResponseEntity<ProductDto>updateProduct(@PathVariable String Id,@RequestBody ProductDto productDto){
        log.info("Request starting for service layer to Update the Product {} :",Id);
        ProductDto update = productService.update(productDto, Id);
        log.info("Request completed for service layer to Update the Product {} :",Id);
        return new ResponseEntity<>(update,HttpStatus.OK);
    }

    @DeleteMapping("/{Id}")
    public ResponseEntity<ApiResponse>delete(@PathVariable String Id){
        log.info("Request starting for service layer to Delete the Product {} :",Id);
        productService.delete(Id);
        ApiResponse build = ApiResponse.builder()
                .message(AppConstants.PRODUCT_DELETE)
                .status(HttpStatus.OK)
                .success(true)
                .build();
        log.info("Request completed for service layer to Delete the Product {} :",Id);
        return new ResponseEntity<>(build,HttpStatus.OK);
    }

    @GetMapping("/{Id}")
    public ResponseEntity<ProductDto>getProduct(@PathVariable String Id){
        log.info("Request starting for service layer to Get the Product {} :",Id);
        ProductDto byId = productService.getById(Id);
        log.info("Request completed for service layer to  Get the Product {} :",Id);
        return  new ResponseEntity<>(byId,HttpStatus.OK);
    }
    @GetMapping
    public ResponseEntity<PageableResponse<ProductDto>>getAll(
            @RequestParam(value="pageNumber",defaultValue = "0",required = false)int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "5",required = false)int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "title",required = false)String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false)String sortDir
    ){
        log.info("Request starting for service layer to Get All the Product");
        PageableResponse<ProductDto> pageResponse = productService.getAll(pageNumber, pageSize, sortBy, sortDir);
        log.info("Request completed for service layer to  Get All the Product");
        return new ResponseEntity<>(pageResponse,HttpStatus.OK);
    }

    @GetMapping("/getAllLive")
    public ResponseEntity<PageableResponse<ProductDto>>getAllLive(
            @RequestParam(value="pageNumber",defaultValue = "0",required = false)int pageNumber,
            @RequestParam(value = "pageSize",defaultValue = "5",required = false)int pageSize,
            @RequestParam(value = "sortBy",defaultValue = "title",required = false)String sortBy,
            @RequestParam(value = "sortDir",defaultValue = "asc",required = false)String sortDir
    ){

        log.info("Request starting for service layer to Get All Live Product");
        PageableResponse<ProductDto> pageResponse = productService.getAllLive(pageNumber, pageSize, sortBy, sortDir);
        log.info("Request completed for service layer to  Get All the Product");
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
        log.info("Request starting for service layer to Search Product By Keyword {} :",keyWord);
        PageableResponse<ProductDto> pageResponse = productService.searchByTitle(keyWord, pageNumber, pageSize, sortBy, sortDir);
        log.info("Request completed for service layer to Search Product By Keyword {} :",keyWord);
        return new ResponseEntity<>(pageResponse,HttpStatus.OK);
    }
    @PostMapping("/image/{productId}")
    public ResponseEntity<ImageResponse>uploadProductImage(
            @PathVariable String productId,
            @RequestParam("productImage")MultipartFile image
            ) throws IOException {
        log.info("Request starting for service layer to Post Product Image {} :",productId);
        String fileName = fileService.uploadFile(image, imagePath);
        ProductDto productDto = productService.getById(productId);
        productDto.setProductImageName(fileName);
        ProductDto updatedProduct = productService.update(productDto, productId);
        ImageResponse productImageIsUploaded = ImageResponse.builder().imageName(updatedProduct.getProductImageName())
                .message(AppConstants.PRODUCT_IMAGE)
                .status(HttpStatus.CREATED)
                .success(true)
                .build();

        log.info("Request completed for service layer to Post Product Image {} :",productId);
        return new ResponseEntity<>(productImageIsUploaded,HttpStatus.CREATED);
    }
    @GetMapping("/image/{productId}")
    public void serveUserImage(@PathVariable String productId, HttpServletResponse response) throws IOException {

        log.info("Request starting for service layer to Get the Product Image {} :",productId);
        ProductDto productDto = productService.getById(productId);
        InputStream resource = fileService.getResource(imagePath, productDto.getProductImageName());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        log.info("Request completed for service layer to  Get the Product Image {} :",productId);
        StreamUtils.copy(resource,response.getOutputStream());

    }
}

