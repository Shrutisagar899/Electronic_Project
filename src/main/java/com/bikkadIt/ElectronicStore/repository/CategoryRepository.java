package com.bikkadIt.ElectronicStore.repository;

import com.bikkadIt.ElectronicStore.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,String> {

}
