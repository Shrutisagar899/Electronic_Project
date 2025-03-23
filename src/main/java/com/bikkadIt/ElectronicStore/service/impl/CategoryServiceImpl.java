package com.bikkadIt.ElectronicStore.service.impl;

import com.bikkadIt.ElectronicStore.dtos.CategoryDto;
import com.bikkadIt.ElectronicStore.dtos.PageableResponse;
import com.bikkadIt.ElectronicStore.entity.Category;
import com.bikkadIt.ElectronicStore.exception.ResourceNotFoundException;
import com.bikkadIt.ElectronicStore.helper.Helper;
import com.bikkadIt.ElectronicStore.repository.CategoryRepository;
import com.bikkadIt.ElectronicStore.service.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public CategoryDto create(CategoryDto categoryDto) {
        //creating categoryId:randomly
        String categoryId = UUID.randomUUID().toString();
        categoryDto.setCatagoryId(categoryId);

        Category category = mapper.map(categoryDto, Category.class);
        Category savedCategory = categoryRepository.save(category);
        return mapper.map(savedCategory, CategoryDto.class);
    }

    @Override
    public CategoryDto update(CategoryDto categoryDto, String categoryId) {
        // get category by id
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + categoryId));

        // update category details
        category.setTitle(categoryDto.getTitle());
        category.setDescription(categoryDto.getDescription());
        category.setCoverImage(categoryDto.getCoverImage());

        Category updatedCategory = categoryRepository.save(category);
        return mapper.map(updatedCategory, CategoryDto.class);
    }

    @Override
    public void delete(String categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + categoryId));

        categoryRepository.delete(category);
    }

    @Override
    public PageableResponse<CategoryDto> getAll() {
        return null;
    }

    @Override
    public PageableResponse<CategoryDto> getAll(int pageNumber, int pageSize, String sortBy) {
        Sort sort = (sortBy.equalsIgnoreCase("desc")) ?
                Sort.by(sortBy).descending() :
                Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Category> page = categoryRepository.findAll(pageable);

        PageableResponse<CategoryDto> pageableResponse = Helper.getPageableResponse(page, CategoryDto.class);

        return pageableResponse;  // Now returning the correct response
    }

    @Override
    public CategoryDto get(String categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + categoryId));

        return mapper.map(category, CategoryDto.class);
    }

    @Override
    public PageableResponse<CategoryDto> getAll(int pageNumber, int pageSize, String sortDir, String sortedBy) {
        return null;
    }
}
