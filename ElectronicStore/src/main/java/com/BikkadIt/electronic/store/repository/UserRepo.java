package com.BikkadIt.electronic.store.repository;

import com.BikkadIt.electronic.store.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepo extends JpaRepository<User, String> {
    Optional<User> findByEmailId(String emailId);

    //Optional<User> findByEmailIdandPassword(String emailId,String Password);

    List<User>findByNameContaining(String keyword);
}

