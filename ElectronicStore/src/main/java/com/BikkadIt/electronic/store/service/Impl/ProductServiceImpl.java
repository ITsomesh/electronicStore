package com.BikkadIt.electronic.store.service.Impl;

import com.BikkadIt.electronic.store.Constants.AppConstants;
import com.BikkadIt.electronic.store.dtos.PageableResponse;
import com.BikkadIt.electronic.store.dtos.ProductDto;
import com.BikkadIt.electronic.store.entities.Category;
import com.BikkadIt.electronic.store.entities.Product;
import com.BikkadIt.electronic.store.exception.ResourceNotFoundException;
import com.BikkadIt.electronic.store.helper.Helper;
import com.BikkadIt.electronic.store.repository.CategoryRepo;
import com.BikkadIt.electronic.store.repository.ProductRepository;
import com.BikkadIt.electronic.store.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.UUID;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Value("${product.image.path}")
    private String imagePath1;
    @Autowired
    private CategoryRepo categoryRepo;


    @Override
    public ProductDto create(ProductDto productDto) {

        Product pro = modelMapper.map(productDto, Product.class);
        //Pro Id
        String proId= UUID.randomUUID().toString();
        pro.setProductId(proId);

        //Date
        pro.setAddedDate(new Date());
        Product savePro = productRepository.save(pro);
        return modelMapper.map(savePro,ProductDto.class);
    }

    @Override
    public ProductDto update(ProductDto productDto, String productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException(AppConstants.PRODUCT_NOT_FOUND));
        product.setTitle(productDto.getTitle());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setDiscountedPrice(productDto.getDiscountedPrice());
        product.setQuantity(productDto.getQuantity());
        product.setLive(productDto.isLive());
        product.setStock(productDto.isStock());
        product.setProductImageName(productDto.getProductImageName());
        Product updatedPro = productRepository.save(product);
        return modelMapper.map(updatedPro,ProductDto.class);
    }

    @Override
    public void delete(String productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException(AppConstants.PRODUCT_DELETE));
        String fullPath=imagePath1+product.getProductImageName();
        try {
            Path path = Paths.get(fullPath);
            Files.delete(path);
        } catch (NoSuchFileException ex){
            log.info("User Image Not Found");
        }
        catch (IOException e ){
            throw new RuntimeException();
        }
        log.info("Request completed to delete productpoii");
        productRepository.delete(product);
    }

    @Override
    public ProductDto getById(String productId) {
        Product productById = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException(AppConstants.PRODUCT_NOT_FOUND));
        return modelMapper.map(productById,ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAll(int pageNumber,int pageSize,String sortBy,String SortDir) {
        Sort sort = (SortDir.equalsIgnoreCase("desc"))
                ? (Sort.by(sortBy).descending())
                : (Sort.by(sortBy).ascending());
        Pageable pageable= PageRequest.of(pageNumber, pageSize,sort);
        Page<Product> page = productRepository.findAll(pageable);
        return Helper.getPageableResponse(page,ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAllLive(int pageNumber,int pageSize,String sortBy,String SortDir) {
        Sort sort = (SortDir.equalsIgnoreCase("desc"))
                ? (Sort.by(sortBy).descending())
                : (Sort.by(sortBy).ascending());
        Pageable pageable= PageRequest.of(pageNumber, pageSize,sort);
        Page<Product> page = productRepository.findAll(pageable);
        return Helper.getPageableResponse(page,ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> searchByTitle(String subTitle, int pageNumber, int pageSize, String sortBy, String SortDir) {
        return null;
    }

    @Override
    public ProductDto createWithCategory(ProductDto productDto, String categoryId) {
        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException(AppConstants.CATEGORY_ID));

        Product pro = modelMapper.map(productDto, Product.class);
        //Pro Id
        String proId= UUID.randomUUID().toString();
        pro.setProductId(proId);

        //Date
        pro.setAddedDate(new Date());
        pro.setCategory(category);
        Product savePro = productRepository.save(pro);
        return modelMapper.map(savePro,ProductDto.class);

    }

    @Override
    public ProductDto updateCategory(String productId, String categoryId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException(AppConstants.PRODUCT_ID));
        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(AppConstants.CATEGORY_ID));
        product.setCategory(category);
        Product save = productRepository.save(product);
        return modelMapper.map(save, ProductDto.class);
    }



    @Override
    public PageableResponse<ProductDto> getAllOfCategory(String categoryId,int pageNumber, int pageSize, String sortBy, String sortDir) {

        Category category = categoryRepo.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException(AppConstants.CATEGORY_ID));

        Sort sort = (sortDir.equalsIgnoreCase("desc"))
                ? (Sort.by(sortBy).descending())
                : (Sort.by(sortBy).ascending());
        PageRequest pageable = PageRequest.of(pageNumber, pageSize,sort);

        Page<Product> page = productRepository.findByCategory(category,pageable);

        return Helper.getPageableResponse(page,ProductDto.class);
    }



//    @Override
//    public PageableResponse<ProductDto> searchByTitle(String subTitle,int pageNumber,int pageSize,String sortBy,String SortDir) {
//        Sort sort = (SortDir.equalsIgnoreCase("desc"))
//                ? (Sort.by(sortBy).descending())
//                : (Sort.by(sortBy).ascending());
//        Pageable pageable= PageRequest.of(pageNumber, pageSize,sort);
//        Page<Product> page = productRepository.findByTitleContaining(subTitle,Pageable);
//        return Helper.getPageableResponse(page,ProductDto.class);

    }

