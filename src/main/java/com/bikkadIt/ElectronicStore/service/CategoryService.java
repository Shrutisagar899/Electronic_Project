package com.bikkadIt.ElectronicStore.service;

import com.bikkadIt.ElectronicStore.dtos.CategoryDto;
import com.bikkadIt.ElectronicStore.dtos.PageableResponse;

public interface CategoryService {
    //create
    CategoryDto create(CategoryDto categoryDto);

    //update
    CategoryDto update(CategoryDto categoryDto, String categoryId);

    //delete
    void delete(String categoryId);

    //getALl
    PageableResponse<CategoryDto> getAll();

     PageableResponse<CategoryDto> getAll(int pageNumber, int pageSize, String sortBy);

    //get single catagory detail
    CategoryDto get(String categoryId);

    PageableResponse<CategoryDto> getAll(int pageNumber, int pageSize, String sortDir, String sortedBy);
}
