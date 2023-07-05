package com.BikkadIt.electronic.store.repository;

import com.BikkadIt.electronic.store.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepo extends JpaRepository<Category,String> {

}
