package com.bikkadIt.ElectronicStore.service.impl;

import com.bikkadIt.ElectronicStore.dtos.PageableResponse;
import com.bikkadIt.ElectronicStore.dtos.ProductDto;
import com.bikkadIt.ElectronicStore.entity.Category;
import com.bikkadIt.ElectronicStore.entity.Product;
import com.bikkadIt.ElectronicStore.exception.ResourceNotFoundException;
import com.bikkadIt.ElectronicStore.helper.Helper;
import com.bikkadIt.ElectronicStore.repository.CategoryRepository;
import com.bikkadIt.ElectronicStore.repository.ProductRepository;
import com.bikkadIt.ElectronicStore.service.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ModelMapper mapper;
    //
    @Autowired
    private CategoryRepository categoryRepository;

    //Other dependancy
    @Override
    public ProductDto create(ProductDto productDto) {
        Product product = mapper.map(productDto, Product.class);
        // product id
        String productId = UUID.randomUUID().toString();
        product.setProductId(productId);
        //added
        product.setAddedDate(new Date());
        Product saveProduct = productRepository.save(product);

        return mapper.map(saveProduct, ProductDto.class);
    }

    @Override
    public ProductDto update(ProductDto productDto, String productId) {
        if (productId == null || productId.isEmpty()) {
            throw new IllegalArgumentException("Product ID must not be null or empty");
        }


        Product existingProduct = productRepository.findById(productId).
                orElseThrow(() -> new ResourceNotFoundException("Product not found with Id :" + productId));

        existingProduct.setProductId(productDto.getProductId());
        existingProduct.setDescription(productDto.getDescription());
        existingProduct.setPrice(productDto.getPrice());
        existingProduct.setQuantity(productDto.getQuantity());
        existingProduct.setDiscountedPrice(productDto.getDiscountedPrice());
        existingProduct.setLive(productDto.isLive());
        existingProduct.setInStock(productDto.isInStock());
        existingProduct.setProductImageName(productDto.getProductImageName());


        //save the entity
        Product updateProduct = productRepository.save(existingProduct);
        return mapper.map(updateProduct, ProductDto.class);
    }


    @Override
    public void delete(String productId) {
        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id:" + productId));
        productRepository.delete(existingProduct);
    }

    @Override
    public ProductDto get(String productId) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product is the missing :" + productId));
        return mapper.map(product, ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> page = productRepository.findAll(pageable);
        return Helper.getPageableResponse(page, ProductDto.class);
    }

    @Override
    public PageableResponse<ProductDto> getAllLive(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> page = productRepository.findByLiveTrue(pageable);
        return Helper.getPageableResponse(page, ProductDto.class);

    }

    @Override
    public PageableResponse<ProductDto> searchByTitle(String subTitle, int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? (Sort.by(sortBy).descending()) : (Sort.by(sortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
        Page<Product> page = productRepository.findByTitleContaining(subTitle, pageable);
        return Helper.getPageableResponse(page, ProductDto.class);
    }

    @Override


    public ProductDto createWithCategory(ProductDto productDto, String categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("category not found!!"));
        Product product = mapper.map(productDto, Product.class);
        // product id
        String productId = UUID.randomUUID().toString();
        product.setProductId(productId);
        //added
        product.setAddedDate(new Date());
        product.setCategory(category);
        Product saveProduct = productRepository.save(product);

        return mapper.map(saveProduct, ProductDto.class);
    }
}


