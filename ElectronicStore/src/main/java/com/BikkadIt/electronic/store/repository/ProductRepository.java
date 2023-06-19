package com.BikkadIt.electronic.store.repository;

import com.BikkadIt.electronic.store.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface  ProductRepository extends JpaRepository<Product,String> {

    List<Product>findByTitleContaining(String subtitle);

    List<Product>findByLiveTrue();

}
