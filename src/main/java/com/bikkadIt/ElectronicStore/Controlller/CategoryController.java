package com.bikkadIt.ElectronicStore.Controlller;

import com.bikkadIt.ElectronicStore.dtos.ApiResponseMessage;
import com.bikkadIt.ElectronicStore.dtos.CategoryDto;
import com.bikkadIt.ElectronicStore.dtos.PageableResponse;
import com.bikkadIt.ElectronicStore.dtos.ProductDto;
import com.bikkadIt.ElectronicStore.service.CategoryService;
import com.bikkadIt.ElectronicStore.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    // Create category
    @PostMapping
    public ResponseEntity<CategoryDto> createCategory(@Valid @RequestBody CategoryDto categoryDto) {
        //call service to save object
        CategoryDto createdCategory = categoryService.create(categoryDto);
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    // Update category
    @PutMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> updateCategory(
            @PathVariable String categoryId,
            @RequestBody CategoryDto categoryDto) {
        CategoryDto updateCategory = categoryService.update(categoryDto, categoryId);
        return new ResponseEntity<>(updateCategory, HttpStatus.OK);
    }

    // Delete category
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<ApiResponseMessage> deleteCategory(@PathVariable String categoryId) {
        categoryService.delete(categoryId);
        ApiResponseMessage responseMessage = ApiResponseMessage.builder()
                .message("Category is deleted successfully")
                .status(HttpStatus.OK)
                .success(true)
                .build();
        return new ResponseEntity<>(responseMessage, HttpStatus.OK);
    }

    // Get all categories with pagination and sorting
    @GetMapping
    public ResponseEntity<PageableResponse<CategoryDto>> getAll(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortedBy", defaultValue = "title", required = false) String sortedBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
        PageableResponse<CategoryDto> pageableResponse = categoryService.getAll(pageNumber, pageSize, sortDir, sortedBy);
        return new ResponseEntity<>(pageableResponse, HttpStatus.OK);
    }

    // Get single category by ID
    @GetMapping("/{categoryId}")
    public ResponseEntity<CategoryDto> getSingle(@PathVariable String categoryId) {
        CategoryDto categoryDto = categoryService.get(categoryId);
        return ResponseEntity.ok(categoryDto);
    }

    //create product with category

    @PostMapping("/{categoryId}/{products}")
    public ResponseEntity<ProductDto> createProductWithCategory(
            @PathVariable("categoryId") String categoryId,
            @RequestBody ProductDto dto
    ) {
        ProductDto productWithCategory = productService.createWithCategory(dto, categoryId);
        return new ResponseEntity<>(productWithCategory, HttpStatus.CREATED);
    }
}
