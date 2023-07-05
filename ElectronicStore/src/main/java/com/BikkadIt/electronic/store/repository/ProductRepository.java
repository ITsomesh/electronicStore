package com.BikkadIt.electronic.store.repository;

import com.BikkadIt.electronic.store.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.List;

public interface  ProductRepository extends JpaRepository<Product,String> {

    //Page<Product>findByTitleContaining(String subTitle,Pageable pageable);

    //Page<Product> findByLiveTrue(Pageable pageable);

}
