package com.BikkadIt.electronic.store.repository;

import com.BikkadIt.electronic.store.entities.Cart;
import com.BikkadIt.electronic.store.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart,String> {

   Optional<Cart> findByUser(User user);

}
