package com.bikkadIt.ElectronicStore.repository;


import com.bikkadIt.ElectronicStore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String> {

    Optional<User> findByEmail(String email);
    Optional<User> findByEmailAndPassword(String email, String password);
    List<User> findByNameContaining(String keyword);


    // void delete(String userId);
}

