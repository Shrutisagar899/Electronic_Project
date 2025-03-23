package com.bikkadIt.ElectronicStore.service;

import com.bikkadIt.ElectronicStore.dtos.PageableResponse;
import com.bikkadIt.ElectronicStore.dtos.ProductDto;
import org.springframework.stereotype.Service;

@Service
public interface ProductService {
    //create
   ProductDto create(ProductDto productDto);

    //update
 ProductDto update(ProductDto productDto, String productId);

//    ProductDto update(String productId, ProductDto productDto);

    //delete
 void delete(String productId);

    //get single
   ProductDto get(String productId);

   //get all
    PageableResponse<ProductDto> getAll(int pageNumber, int pageSize, String sortBy, String Dir);

    //get all : live
    PageableResponse<ProductDto> getAllLive(int pageNumber, int pageSize, String sortBy, String Dir);

    //search product
    PageableResponse<ProductDto> searchByTitle(String subTitle,int pageNumber, int pageSize, String sortBy, String Dir);

      //creat product with category
    ProductDto createWithCategory (ProductDto productDto, String categoryId);

    //other method

}
