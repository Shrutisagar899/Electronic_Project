package com.bikkadIt.ElectronicStore.helper;

import com.bikkadIt.ElectronicStore.dtos.PageableResponse;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class Helper {

    private static ModelMapper modelMapper = new ModelMapper(); // Declare ModelMapper as a class-level variable to avoid recreating it each time.


    public static <U, V> PageableResponse<V> getPageableResponse(Page<U> page, Class<V> type) {
        // Get the list of entities (U)
        List<U> entity = page.getContent();

        // Convert entities (U) to DTOs (V) using ModelMapper
        List<V> dtoList = entity.stream()
                .map(object -> modelMapper.map(object, type))
                .collect(Collectors.toList());

        // Create PageableResponse for the DTOs (V)
        PageableResponse<V> pageableResponse = new PageableResponse<>();
        pageableResponse.setContent(dtoList);
        pageableResponse.setPageNumber(page.getNumber());
        pageableResponse.setPageSize(page.getSize());
        pageableResponse.setTotalElements(page.getTotalElements()); // Corrected typo
        pageableResponse.setTotalPages(page.getTotalPages());
        pageableResponse.setLastPage(page.isLast());

        return pageableResponse;
    }
}
