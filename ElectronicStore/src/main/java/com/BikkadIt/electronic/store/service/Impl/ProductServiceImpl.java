package com.BikkadIt.electronic.store.service.Impl;

import com.BikkadIt.electronic.store.dtos.PageableResponse;
import com.BikkadIt.electronic.store.dtos.ProductDto;
import com.BikkadIt.electronic.store.entities.Product;
import com.BikkadIt.electronic.store.exception.ResourceNotFoundException;
import com.BikkadIt.electronic.store.helper.Helper;
import com.BikkadIt.electronic.store.repository.ProductRepository;
import com.BikkadIt.electronic.store.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper modelMapper;


    @Override
    public ProductDto create(ProductDto productDto) {
        Product pro = modelMapper.map(productDto, Product.class);
        Product savePro = productRepository.save(pro);
        return modelMapper.map(savePro,ProductDto.class);
    }

    @Override
    public ProductDto update(ProductDto productDto, String productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product Not Found By Given ProductId"));
        product.setTitle(productDto.getTitle());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
        product.setDiscountedPrice(productDto.getDiscountedPrice());
        product.setQuantity(productDto.getQuantity());
        product.setLive(productDto.isLive());
        product.setStock(productDto.isStock());
        Product updatedPro = productRepository.save(product);
        return modelMapper.map(updatedPro,ProductDto.class);
    }

    @Override
    public void delete(String productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product Not Found By Given ProductId"));
        productRepository.delete(product);
    }

    @Override
    public ProductDto getById(String productId) {
        Product productById = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product Not Found By Given ProductId"));
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
    public PageableResponse<ProductDto> searchByTitle(String subTitle,int pageNumber,int pageSize,String sortBy,String SortDir) {
        Sort sort = (SortDir.equalsIgnoreCase("desc"))
                ? (Sort.by(sortBy).descending())
                : (Sort.by(sortBy).ascending());
        Pageable pageable= PageRequest.of(pageNumber, pageSize,sort);
        Page<Product> page = productRepository.findByTitleContaining(subTitle, (java.awt.print.Pageable) pageable);
        return Helper.getPageableResponse(page,ProductDto.class);

    }
}
