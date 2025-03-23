package com.bikkadIt.ElectronicStore.repository;

import com.bikkadIt.ElectronicStore.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product,String> {

        //search
    org.springframework.data.domain.Page<Product> findByTitleContaining(String subTitle, Pageable pageable);
    Page<Product> findByLiveTrue(Pageable pageable);

    // other methods
    //custome finder method
    // query method
}
